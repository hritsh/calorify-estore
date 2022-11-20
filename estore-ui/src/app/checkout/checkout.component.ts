import { Component, Input, OnInit } from '@angular/core';
import { Product } from '../product';
import { ShoppingCartService } from '../shopping-cart.service';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../user.service';
import { User } from '../user';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  @Input() username!: string;
  cart: Product[] = [];
  @Input() user?: User;
  ifSucceed: Boolean = false;
  ifFailed: Boolean = false;

  constructor(
    private shoppingCartService: ShoppingCartService,
    private userService: UserService,
    private location: Location,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.ifSucceed = false;
    this.ifFailed = false;
    this.getUser();
    this.getCart();
    this.getTotalCalories();
    this.getTotalPrice();
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
    this.username = localStorage.getItem('sub')!;
    this.userService.getUser(this.username)
      .subscribe(user => this.user = user);
  }

  getTotalCalories(): number {
    let totalCalories = 0;
    for (let i = 0; i < this.cart.length; i++) {
      totalCalories += this.cart[i].calories * this.cart[i].quantity;
    }
    return totalCalories;
  }

  getTotalPrice(): number {
    let totalPrice = 0;
    for (let i = 0; i < this.cart.length; i++) {
      totalPrice += this.cart[i].price * this.cart[i].quantity;
    }
    return totalPrice;
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
    console.log(this.ifSucceed, this.ifFailed, this.cart);
  }

}
