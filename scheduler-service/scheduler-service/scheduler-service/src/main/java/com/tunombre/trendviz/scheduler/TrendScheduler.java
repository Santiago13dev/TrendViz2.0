package com.tunombre.trendviz.scheduler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class TrendScheduler {

    private final WebClient newsClient;
    private final InfluxDB influxDB;
    private final String apiKey;
    private final ArticleRepository articleRepo;

    public TrendScheduler(
        InfluxDB influxDB,
        ArticleRepository articleRepo,
        @Value("${newsapi.key}") String apiKey
    ) {
        this.influxDB = influxDB;
        this.articleRepo = articleRepo;
        this.apiKey = apiKey;
        this.newsClient = WebClient.builder()
            .baseUrl("https://newsapi.org/v2")
            .build();
    }

    @Scheduled(fixedRateString = "${fetch.interval-ms}")
    public void fetchConflicts() {
        List<String> countries = List.of("china", "japan", "south korea", "taiwan");
        countries.forEach(country -> {
            try {
                // 1. Llamada a NewsAPI
                NewsResponse resp = newsClient.get()
                    .uri(uri -> uri.path("/everything")
                        .queryParam("q", country + " AND (guerra OR conflicto)")
                        .queryParam("language", "es")
                        .queryParam("apiKey", apiKey)
                        .build())
                    .retrieve()
                    .bodyToMono(NewsResponse.class)
                    .block();

                // 2. Guardar artículos en MongoDB
                if (resp != null && !resp.getArticles().isEmpty()) {
                    List<Article> docs = resp.getArticles().stream()
                        .map(a -> new Article(country, a.getTitle(), a.getUrl(), a.getPublishedAt()))
                        .toList();
                    articleRepo.saveAll(docs);
                }

                // 3. Contar y escribir en InfluxDB
                long count = resp != null ? resp.getArticles().size() : 0;
                Point point = Point
                    .measurement("conflict_count")
                    .tag("country", country)
                    .addField("articles", count)
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .build();
                influxDB.write(point);

                System.out.println("✅ [" + country + "] " + count + " artículos guardados y métrica enviada");

            } catch (WebClientResponseException e) {
                // Manejo de errores de API (por ejemplo, 401 Unauthorized)
                System.err.println("⚠️ Error NewsAPI para '" + country + "': " +
                    e.getStatusCode() + " - " + e.getResponseBodyAsString());
            } catch (Exception e) {
                // Cualquier otro fallo
                System.err.println("❌ Error inesperado al procesar '" + country + "': " + e.getMessage());
            }
        });
    }
}
