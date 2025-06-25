package com.tunombre.trendviz.scheduler;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "articles")
public class Article {

    @Id
    private String id;
    private String country;
    private String title;
    private String url;
    private Instant publishedAt;

    public Article() {}

    public Article(String country, String title, String url, Instant publishedAt) {
        this.country = country;
        this.title = title;
        this.url = url;
        this.publishedAt = publishedAt;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Instant getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }
}
