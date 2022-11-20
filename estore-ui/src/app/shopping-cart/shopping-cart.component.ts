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
import { Salad } from '../salad';

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
  ifDeleteSucceed: Boolean = false;
  ifDeleteFailed: Boolean = false;
  s: Salad = new Salad(this.userService);


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
    this.ifDeleteSucceed = false;
    this.ifDeleteFailed = false;
    this.getUser();
    this.getCart();
    this.getTotalCalories();
    this.getTotalPrice();
    this.s.getUserSalad();
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
    this.username = localStorage.getItem("sub") as string;
    this.userService.getUser(this.username)
      .subscribe(user => this.user = user);
  }

  getTotalCalories(): number {
    let totalCalories = 0;
    for (let i = 0; i < this.cart.length; i++) {
      totalCalories += this.cart[i].calories * this.cart[i].quantity;
    }
    totalCalories += this.s.calories;
    return totalCalories;
  }

  getTotalPrice(): number {
    let totalPrice = 0;
    for (let i = 0; i < this.cart.length; i++) {
      totalPrice += this.cart[i].price * this.cart[i].quantity;
    }
    totalPrice += this.s.price;
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
    this.s.deleteSalad();
    this.s.calculate();
    this.shoppingCartService.checkout(this.username).subscribe(output => {
      if (output) {
        this.ifSucceed = true;
        this.cart = [];
      } else { this.ifFailed = true; }
    });
  }
  clear(): void {
    this.s.deleteSalad();
    this.shoppingCartService.clearCart(this.username).subscribe(output => {
      if (output) {
        this.ifDeleteSucceed = true;
        this.cart = [];
      } else { this.ifDeleteFailed = true; }
    });
  }
}

