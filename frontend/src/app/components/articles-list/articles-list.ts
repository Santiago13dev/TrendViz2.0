import { Component, ViewChild, OnInit } from '@angular/core';
import { CommonModule }                 from '@angular/common';
import { TrendService }                 from '../../services/trend.service';
import { Article }                      from '../../models/article';
import { environment }                  from '../../../environments/environment';
import { CountChartComponent }          from '../count-chart/count-chart';

@Component({
  standalone : true,
  selector   : 'app-articles-list',
  templateUrl: './articles-list.html',
  styleUrls  : ['./articles-list.scss'],
  imports    : [CommonModule, CountChartComponent]
})
export class ArticlesListComponent implements OnInit {

  articles: Article[] = [];

  page = 0;
  readonly size = 5;

  /** para actualizar la gráfica */
  @ViewChild(CountChartComponent) chart?: CountChartComponent;

  constructor(private trend: TrendService) {}

  ngOnInit(): void {
    this.loadArticles();
    this.loadCounts();
  }

  /* ---------- navegación ---------- */
  next(): void { this.page++; this.loadArticles(); }
  prev(): void { if (this.page > 0) { this.page--; this.loadArticles(); } }

  /* ---------- llamadas al servicio ---------- */
  private loadArticles(): void {
    this.trend.getArticles(this.page, this.size)
      .subscribe(arts => this.articles = arts);
  }

  private loadCounts(): void {
    this.trend.getCounts()
      .subscribe(c => {
        if (this.chart) {
          this.chart.updateChart(
            c.map(x => x.country),
            c.map(x => x.value)
          );
        }
      });
  }
}
