import { TestBed } from '@angular/core/testing';

import { Trend } from './trend';

describe('Trend', () => {
  let service: Trend;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Trend);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
