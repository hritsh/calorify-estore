import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../product.service';
import { Location } from '@angular/common';
import { UserService } from '../user.service';
import { Salad } from '../salad';


@Component({
  selector: 'app-salad-maker',
  templateUrl: './salad-maker.component.html',
  styleUrls: ['./salad-maker.component.css']
})
export class SaladMakerComponent implements OnInit {
  @Input() username!: any;

  Object = Object;
  s: Salad = new Salad(this.userService);

  constructor(
    private productService: ProductService,
    private userService: UserService,
    private route: ActivatedRoute,
    private location: Location,
  ) { }

  ngOnInit(): void {
    this.s.getUserSalad();
    this.s.initCheckboxes();
  }

  /**
    * Brings the {@linkplain User user} back tot heir previous page
    */
  backButton(): void {
    this.location.back();
  }
}
