import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.scss']
})
export class ResultComponent implements OnInit {

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
