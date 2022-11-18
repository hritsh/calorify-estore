import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './bmi-home.component.html',
  styleUrls: ['./bmi-home.component.scss']
})
export class BMIHomeComponent implements OnInit {

  age = 25;
  height = 135;
  sex = "Male";
  weight = 60;

  constructor(private router: Router) { }
  ngOnInit(): void {
  }

  changeHeight(event: any) {
    this.height = event.target.value;
  }

  male() {
    this.sex = "Male"
  }

  female() {
    this.sex = "Female"
  }
  calculateBMI() {
    const BMI = this.weight / Math.pow(this.height / 100, 2);
    this.router.navigate(['/bmi/result', BMI.toFixed(1)])
  }
}
