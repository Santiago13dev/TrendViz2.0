import 'zone.js';                         // 1  ← zone.js primero
import { enableProdMode } from '@angular/core';          // 2
import { bootstrapApplication } from '@angular/platform-browser'; // 3
import { provideHttpClient } from '@angular/common/http';         // 4

import { App } from './app/app';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(App, {
  providers: [
    provideHttpClient()
  ]
})
.catch(err => console.error(err));
