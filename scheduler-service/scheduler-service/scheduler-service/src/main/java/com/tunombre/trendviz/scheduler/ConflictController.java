package com.tunombre.trendviz.scheduler;

import java.util.List;
import java.util.stream.Collectors;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conflicts")
public class ConflictController {

    private final ArticleRepository articleRepo;
    private final InfluxDB influxDB;

    public ConflictController(ArticleRepository articleRepo, InfluxDB influxDB) {
        this.articleRepo = articleRepo;
        this.influxDB = influxDB;
    }

    /** 
     * Devuelve la serie temporal de conteos de artículos por país.
     * Cada registro es un CountRecord { country, time, articles } 
     */
    @GetMapping("/count")
    public List<CountRecord> getCounts() {
        QueryResult result = influxDB.query(new Query(
            "SELECT country, articles FROM conflict_count", 
            "trendviz"
        ));

        if (result == null || result.getResults() == null) {
            return List.of();
        }

        return result.getResults().stream()
            // Evitar resultados sin series
            .filter(r -> r.getSeries() != null)
            .flatMap(r -> r.getSeries().stream())
            // Filtrar series sin tags o sin tag "country"
            .filter(series -> series.getTags() != null 
                    && series.getTags().get("country") != null)
            .flatMap(series ->
                series.getValues().stream()
                    .map(vals -> new CountRecord(
                        series.getTags().get("country"),
                        vals.get(0).toString(),
                        ((Number) vals.get(1)).intValue()
                    ))
            )
            .collect(Collectors.toList());
    }

    /**
     * Devuelve una lista paginada de artículos.
     */
    @GetMapping("/articles")
    public List<Article> getArticles(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        return articleRepo.findAll(pageable).getContent();
    }
}
