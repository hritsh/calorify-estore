import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BMIHomeComponent } from './bmi-home.component';

describe('BMIHomeComponent', () => {
  let component: BMIHomeComponent;
  let fixture: ComponentFixture<BMIHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BMIHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BMIHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
