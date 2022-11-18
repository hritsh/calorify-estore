import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BMIResultComponent } from './bmi-result.component';

describe('BMIResultComponent', () => {
  let component: BMIResultComponent;
  let fixture: ComponentFixture<BMIResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BMIResultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BMIResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
