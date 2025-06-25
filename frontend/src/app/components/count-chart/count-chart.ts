import { Component }                              from '@angular/core';
import { NgChartsModule }                         from 'ng2-charts';
import { ChartType, ChartData, ChartOptions }     from 'chart.js';

@Component({
  standalone : true,
  selector   : 'app-count-chart',
  templateUrl: './count-chart.html',
  styleUrls  : ['./count-chart.scss'],
  imports    : [NgChartsModule]
})
export class CountChartComponent {

  /** El tipo debe ser el literal `'bar'` */
  chartType: 'bar' = 'bar';

  chartData: ChartData<'bar'> = {
    labels   : [],
    datasets : [{ label: 'Artículos', data: [] }]
  };

  chartOptions: ChartOptions<'bar'> = { responsive: true };

  /** Se llamará desde el componente padre */
  updateChart(labels: string[], values: number[]): void {
    this.chartData = {
      labels,
      datasets: [{ label: 'Artículos', data: values }]
    };
  }
}
