package com.tunombre.trendviz.scheduler;

import java.util.List;

public class NewsResponse {
    private List<NewsApiArticle> articles;

    public List<NewsApiArticle> getArticles() { return articles; }
    public void setArticles(List<NewsApiArticle> articles) { this.articles = articles; }
}
