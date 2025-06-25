import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CountChart } from './count-chart';

describe('CountChart', () => {
  let component: CountChart;
  let fixture: ComponentFixture<CountChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CountChart]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CountChart);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
