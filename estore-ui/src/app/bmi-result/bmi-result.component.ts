import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-result',
  templateUrl: './bmi-result.component.html',
  styleUrls: ['./bmi-result.component.scss']
})
export class BMIResultComponent implements OnInit {

  bmi: number;
  result: string;
  interpretation: string;

  constructor(private route: ActivatedRoute) {
    this.bmi = +route.snapshot.paramMap.get('value')!;
    this.result = '';
    this.interpretation = '';
  }
  ngOnInit(): void {
    this.getResult();
  }
  getResult() {
    if (this.bmi >= 40) {
      this.result = 'Obese';
      this.interpretation = 'You have a much higher body weight than normal. You must lead a much healthier lifestyle.Try out our signature customized salads. You should consume 2,310 calories each day. This number is your goal caloric intake.:)'
    } else if (this.bmi >= 25) {
      this.result = 'Over weight';
      this.interpretation = 'You have a body weight that is higher than normal. Try to exercise more and as your mom used to say "Eat more of green leafy veggies". You should consume a maximum of 1500 calories per day.'
    } else if (this.bmi >= 18.5) {
      this.result = 'Normal weight';
      this.interpretation = 'You have a normal body weight. Nice job! Consume around 2000 calories per day.'
    } else {
      this.result = 'Under weight';
      this.interpretation = 'You have a lower body weight than normal. Try to eat a little more. Consume around 2800 calories per day.'
    }
  }

}
