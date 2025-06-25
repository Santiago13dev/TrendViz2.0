// app.ts  ───────────────
import { Component, importProvidersFrom } from '@angular/core';
import { bootstrapApplication }            from '@angular/platform-browser';
import { provideHttpClient }               from '@angular/common/http';
import { RouterModule, RouterOutlet }      from '@angular/router';

import { NgChartsModule }                  from 'ng2-charts';

import { ArticlesListComponent }           from './components/articles-list/articles-list';

@Component({
  standalone : true,
  selector   : 'app-root',
  templateUrl: './app.html',
  styleUrls  : ['./app.scss'],
  // ¡ojo! solo standalone / RouterOutlet
  imports    : [RouterOutlet, ArticlesListComponent]
})
export class App {
  title = 'TrendViz Front';
}

bootstrapApplication(App, {
  providers: [
    provideHttpClient(),
    importProvidersFrom(NgChartsModule, RouterModule)   // Charts + Router
  ]
}).catch(err => console.error(err));
