/**
 * SWEN 261
 * shopping-cart.component.ts
 * 
 * The component that displays the current {@linkplain User user's} shopping cart
 * 
 * Contributors: Team-E
 */

import { Component, Input, OnInit } from '@angular/core';
import { Product } from '../product';
import { ShoppingCartService } from '../shopping-cart.service';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../user.service';
import { User } from '../user';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  @Input() username!: string;
  cart: Product[] = [];
  @Input() user?: User;
  ifSucceed: Boolean = false;
  ifFailed: Boolean = false;
  ingredientsArray: any = ["Lettuce", "Tomato", "Cucumber", "Carrot", "Celery", "Pepper", "Broccoli", "Spinach", "Cabbage", "Olives", "Mushroom", "Garlic", "Chicken"];
  vegetableDict: any = {
    "Lettuce": [0.5, 5], "Tomato": [0.5, 5], "Cucumber": [0.5, 5], "Carrot": [0.5, 5], "Celery": [0.5, 5], "Pepper": [0.5, 5], "Broccoli": [0.5, 5], "Spinach": [0.5, 5], "Cabbage": [0.5, 5], "Olives": [0.5, 5], "Mushroom": [0.5, 5], "Garlic": [0.5, 5], "Chicken": [0.5, 5]
  }
  salad: String = "0-0000000000000-0000000-000000-0-0-0-0";
  price: number = 0;
  calories: number = 0;
  vegetables: string = "0000000000000";
  ingredients: string[] = [];

  constructor(
    private shoppingCartService: ShoppingCartService,
    private userService: UserService,
    private location: Location,
    private route: ActivatedRoute
  ) { }

  /**
   * The initialization of this component
   */
  ngOnInit(): void {
    this.ifSucceed = false;
    this.ifFailed = false;
    this.getUser();
    this.getCart();
    this.getTotalCalories();
    this.getTotalPrice();
    this.getUserSalad();
  }

  /**
   * Gets and saves the shopping cart of the currently logged in {@linkplain User user}
   */
  getCart(): void {
    /**
     * Gets the id from the route to get the cart
     */
    this.shoppingCartService.getCart(this.username)
      .subscribe(cart => this.cart = cart);
  }

  /**
   * Gets the information of the currently logged in {@linkplain User user}
   */
  getUser(): void {
    this.username = this.route.snapshot.paramMap.get('username') as string;
    this.userService.getUser(this.username)
      .subscribe(user => this.user = user);
  }

  getTotalCalories(): number {
    let totalCalories = 0;
    for (let i = 0; i < this.cart.length; i++) {
      totalCalories += this.cart[i].calories * this.cart[i].quantity;
    }
    totalCalories += this.calories;
    return totalCalories;
  }

  getTotalPrice(): number {
    let totalPrice = 0;
    for (let i = 0; i < this.cart.length; i++) {
      totalPrice += this.cart[i].price * this.cart[i].quantity;
    }
    totalPrice += this.price;
    return totalPrice;
  }

  /**
   * Deletes a {@linkplain Product product} from the current {@link User user's} shopping cart
   * @param product The {@link Product product} that is to be deleted
   */
  deleteProduct(product: Product): void {
    /**
     * Initilized with a button and deletes the product from the cart
     * 
     * Input Argument:
     * product -- The product to be deleted
     */

    this.cart = this.cart.filter(p => p !== product);
    this.shoppingCartService.deleteProduct(product, this.username).subscribe();
  }

  /**
   * Returns the {@linkplain User user} to their previous page
   */
  backButton(): void {
    this.location.back();
  }

  /**
   * Initiates the action of checking out for the {@linkplain User user}
   */
  checkout(): void {
    this.shoppingCartService.checkout(this.username).subscribe(output => {
      if (output) {
        this.ifSucceed = true;
        this.cart = [];
      } else { this.ifFailed = true; }
    });
  }

  /**
   * Gets the information of the currently logged in {@linkplain User user}
   */
  getUserSalad(): void {
    this.userService.getSalad(this.username).subscribe(salad => {
      this.salad = salad.toString();
      console.log(this.salad);
      var saladArray = this.salad.split("-");
      this.vegetables = saladArray[0];
      this.price = Number(saladArray[1]);
      this.calories = Number(saladArray[2]);
      for (let i = 0; i < this.vegetables.length; i++) {
        if (this.vegetables.charAt(i) == "1") {
          this.ingredients.push(this.ingredientsArray[i]);
        }
      }
    });
  }

  deleteSalad(): void {
    this.userService.setSalad(this.username, "0-0000000000000-0000000-000000-0-0-0-0").subscribe(() => this.salad = "0-0000000000000-0000000-000000-0-0-0-0");
    this.vegetables = "0000000000000";
    this.price = 0;
    this.calories = 0;
  }
}

