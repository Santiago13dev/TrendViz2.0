package com.tunombre.trendviz.scheduler;

public class CountRecord {
    private String country;
    private String time;
    private int articles;

    public CountRecord(String country, String time, int articles) {
        this.country = country;
        this.time = time;
        this.articles = articles;
    }
    // getters y setters
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public int getArticles() { return articles; }
    public void setArticles(int articles) { this.articles = articles; }
}
