// src/app/services/trend.service.ts
import { Injectable }              from '@angular/core';
import { HttpClient, HttpParams }  from '@angular/common/http';
import { Observable, tap, catchError, throwError } from 'rxjs';

import { Article }                 from '../models/article';
import { environment }             from '../../environments/environment';

/** DTO sencillo para el conteo que devuelve /conflicts/count */
export interface CountryCount {
  country: string;
  value  : number;
}

@Injectable({ providedIn: 'root' })
export class TrendService {

  /** Prefijo configurable: ''
   *  → usa proxy (local)
   *  'http://api.midominio.com' → deploy real
   */
  private readonly api = environment.backendUrl ?? '';

  constructor(private http: HttpClient) {}

  /** Lista paginada de artículos */
  getArticles(page = 0, size = 5): Observable<Article[]> {
    const params = new HttpParams()
                     .set('page', page)
                     .set('size', size);

    return this.http
      .get<Article[]>(`${this.api}/conflicts/articles`, { params })
      .pipe(
        tap(a => console.table(a)),            // 👀 quitar cuando ya no haga falta
        catchError(err => {
          console.error('[TrendService] getArticles', err);
          return throwError(() => err);
        })
      );
  }

  /** Conteo de artículos por país (para la gráfica) */
  getCounts(): Observable<CountryCount[]> {
    return this.http
      .get<CountryCount[]>(`${this.api}/conflicts/count`)
      .pipe(
        tap(c => console.table(c)),            // 👀 quitar cuando ya no haga falta
        catchError(err => {
          console.error('[TrendService] getCounts', err);
          return throwError(() => err);
        })
      );
  }
}
