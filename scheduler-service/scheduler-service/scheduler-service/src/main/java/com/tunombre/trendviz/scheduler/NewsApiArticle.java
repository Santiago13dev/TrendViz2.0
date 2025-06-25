package com.tunombre.trendviz.scheduler;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class NewsApiArticle {
    private String title;
    private String url;
    @JsonProperty("publishedAt")
    private Instant publishedAt;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Instant getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }
}
