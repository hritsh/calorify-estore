import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { Location } from '@angular/common';
import { UserService } from '../user.service';


@Component({
  selector: 'app-salad-maker',
  templateUrl: './salad-maker.component.html',
  styleUrls: ['./salad-maker.component.css']
})
export class SaladMakerComponent implements OnInit {
  @Input() username!: any;
  public message: string;
  salad: String = "0000000000000-0-0";
  price: number = 0;
  calories: number = 0;
  ingredientString: string = "0000000000000";

  Object = Object;
  ingredientsDict: any = {
    "Lettuce": [0.5, 5], "Tomato": [0.5, 5], "Cucumber": [0.5, 5], "Carrot": [0.5, 5], "Celery": [0.5, 5], "Pepper": [0.5, 5], "Broccoli": [0.5, 5], "Spinach": [0.5, 5], "Cabbage": [0.5, 5], "Olives": [0.5, 5], "Mushroom": [0.5, 5], "Garlic": [0.5, 5], "Chicken": [0.5, 5]
  }

  constructor(
    private productService: ProductService,
    private userService: UserService,
    private route: ActivatedRoute,
    private location: Location,
  ) {
    this.message = "";
  }

  ngOnInit(): void {
    this.getUserSalad();
    this.initCheckboxes();
  }

  onChange(name: string, i: number, event: any) {
    if (event.target.checked) {
      this.ingredientString = this.ingredientString.substring(0, i) + "1" + this.ingredientString.substring(i + 1);
      this.price += this.ingredientsDict[name][0];
      this.calories += this.ingredientsDict[name][1];
    }
    else {
      this.ingredientString = this.ingredientString.substring(0, i) + "0" + this.ingredientString.substring(i + 1);
      this.price -= this.ingredientsDict[name][0];
      this.calories -= this.ingredientsDict[name][1];
    }
    console.log(this.ingredientString);
    console.log(this.price);
    console.log(this.calories);
    document.getElementById("salad")!.innerHTML = "Salad: " + this.salad.toString() + "<br>" + this.ingredientString;
    document.getElementById("price")!.innerHTML = "Total Price: $" + this.price;
    document.getElementById("calories")!.innerHTML = "Total Calories: " + this.calories;
  }

  /**
    * Brings the {@linkplain User user} back tot heir previous page
    */
  backButton(): void {
    this.location.back();
  }

  /**
   * Gets the information of the currently logged in {@linkplain User user}
   */
  getUserSalad(): void {
    this.username = (localStorage.getItem('sub')!);
    this.userService.getSalad(this.username).subscribe(salad => {
      this.salad = salad.toString();
      console.log(this.salad);
      var saladArray = this.salad.split("-");
      this.ingredientString = saladArray[0];
      this.price = Number(saladArray[1]);
      this.calories = Number(saladArray[2]);
    });
  }

  /**
   * Handles the action of adding a product to a customer's shopping cart
   * @param product The {@linkplain Product product} to add
   * @param quantity The quantity to add to their cart
   */
  addToCart(): void {
    /**
     * Takes in a product to add to the user's cart
     */
    var username = (localStorage.getItem('sub')!);
    this.salad = this.ingredientString + "-" + this.price + "-" + this.calories;
    this.userService.setSalad(username, this.salad.toString()).subscribe(() => console.log(this.salad));
  }

  initCheckboxes() {
    this.username = (localStorage.getItem('sub')!);
    this.userService.getSalad(this.username).subscribe(salad => {
      this.salad = salad.toString();
      console.log(this.salad);
      var saladArray = this.salad.split("-");
      this.ingredientString = saladArray[0];
      this.price = Number(saladArray[1]);
      this.calories = Number(saladArray[2]);
      for (var i = 0; i < 13; i++) {
        if (this.ingredientString.charAt(i) == "1") {
          document.getElementById(i.toString())!.setAttribute("checked", "true");
          document.getElementById("salad")!.innerHTML = "Salad: " + this.salad.toString() + "<br>" + this.ingredientString;
          document.getElementById("price")!.innerHTML = "Total Price: $" + this.price;
          document.getElementById("calories")!.innerHTML = "Total Calories: " + this.calories;
        }
      }
    });
  }

}
