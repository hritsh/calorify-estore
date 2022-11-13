/***
 * SWEN 261
 * user-product-view.components.ts
 *
 * This component handles the page that showcases a {@linkplain Product product's} details for a customer
 *
 * Contributors: Team-E
 */

 import { Component, Input, OnInit } from '@angular/core';
 import { ActivatedRoute } from '@angular/router';
 import { Product } from '../product';
 import { ProductService } from '../product.service';
 import { Location } from '@angular/common';
 import { ShoppingCartService } from '../shopping-cart.service';

 @Component({
   selector: 'app-user-product-view',
   templateUrl: './user-product-view.component.html',
   styleUrls: ['./user-product-view.component.css']
 })
 export class UserProductViewComponent implements OnInit {
   @Input() product?: Product;
   currAmount: number = 1;
   ifDisplay: boolean = true;
   public message: string;

   constructor(
     private productService: ProductService,
     private shoppingCartService: ShoppingCartService,
     private route: ActivatedRoute,
     private location: Location,

   ) { this.message = ""; }

   /**
    * Initialization of this component
    */
   ngOnInit(): void {
     this.getProduct();
   }

   /**
    * Gets the information of a {@linkplain Product product} in the inventory to showcase
    * To the customer
    */
   getProduct(): void {
     /**
      * Gets the id of the current product page
      */
     const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);

     this.productService.getProduct(id)
       .subscribe(product => this.product = product)
   }

   /**
    * Brings the {@linkplain User user} back tot heir previous page
    */
   backButton(): void {
     this.location.back();
   }

   /**
    * Handles the action of adding a product to a customer's shopping cart
    * @param product The {@linkplain Product product} to add
    * @param quantity The quantity to add to their cart
    */
   addToCart(product: Product, quantity: number): void {
     /**
      * Takes in a product to add to the user's cart
      */
     var username = (localStorage.getItem('sub')!);
     var token = (localStorage.getItem('token'));
     if(username!=null && token != null) {
      this.shoppingCartService.addToCart(product, quantity, username).subscribe(product => {
        product.push(product);
      });
      this.message = "Item Added To Cart Successfully!";
     } else {
      this.message = "Please login or create an account";
     }
   }

   /**
    * This method handles updating the number that is being inputted from a slider
    * The slider handles adding the quantity of a product the customer wishes to add
    * To their shopping cart
    *
    * @param value The value the slider is currently at
    */
   amountInput(value: string): void {
     this.currAmount = +value;
   }
 }
