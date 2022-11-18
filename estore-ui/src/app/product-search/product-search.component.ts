/**
 * SWEN 261
 * product-search.component.ts
 *
 * This is the component that handles the {@linkplain User users} searching for a {@link Product product}
 *
 * Contributors: Team-E
 */

 import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
 import { Observable, Subject } from 'rxjs';
 import { LocalStorageService } from '../local-storage.service';

 import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';
 import { Product } from '../product';
 import { ProductService } from '../product.service';
 import { User } from '../user';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, AbstractControl, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';

 @Component({
   selector: 'app-product-search',
   templateUrl: './product-search.component.html',
   styleUrls: ['./product-search.component.css']
 })
 export class ProductSearchComponent implements OnInit {
   products$!: Observable<Product[]>;
   searchCalories!:FormGroup;
   searchPrice!:FormGroup;
   private searchTerms = new Subject<string>();
   @Input() username!: any;
   @Input() inventory!:Product[];
   @Output() inventoryChange = new EventEmitter();
   currAmount!: number;
   updatedInventory: Product[] = [];
   error!: number;

   constructor(private productService: ProductService, private localStorage: LocalStorageService, public router: Router, public formBuilder: FormBuilder) { }

   /**
    * Method that handles input from the serach bar
    * This updates the string saved in this component to constantly update the results
    * Whenever a {@linkplain User user} inputs another character in the serach box
    *
    * @param term The string that is currently being searched for
    */
   search(term: string): void {
     this.searchTerms.next(term);
   }

   /**
    * Upon initialization this method gets the {@linkplain Product products} that matches this component's search string
    */
   ngOnInit(): void {
    this.searchCalories = this.formBuilder.group({
      startingCal: [''],
      endingCal: ['', this.calValidator()]
    })
    this.searchPrice = this.formBuilder.group({
      startingPrice: [''],
      endingPrice: ['', this.priceValidator()]
    })
     this.username = localStorage.getItem('sub');
     this.products$ = this.searchTerms.pipe(
       // wait 300ms after each keystroke before considering the term
       debounceTime(300),

       // ignore new term if same as previous term
       distinctUntilChanged(),

       // switch to new search observable each time the term changes
       switchMap((term: string) => this.productService.searchProducts(term)),
     );
   }
   priceValidator(): ValidatorFn {
    return (control: AbstractControl) : ValidationErrors | null => {
        const startingPrice = control.root.get('startingPrice')?.value;
        const endingPrice = control.value;
        if(endingPrice>=startingPrice) {
          return null;
        } else {
          return {rangeNotValid:true}
        }
    }
  }
   calValidator(): ValidatorFn {
    return (control: AbstractControl) : ValidationErrors | null => {
        const startingCal = control.root.get('startingCal')?.value;
        const endingCal = control.value;
        if(endingCal>=startingCal) {
          return null;
        } else {
          return {rangeNotValid:true}
        }
    }
  }
   getHighestCalorie(): number {
    return Math.max(...this.inventory.map(function(product: Product) { return product.calories;}))
   }
   descendingPrice(): void {
    this.inventory.sort((prod1, prod2) => (prod1.price > prod2.price ? -1 : 1));
    this.inventoryChange.emit(this.inventory);
   }
   ascendingPrice(): void {
    this.inventory.sort((prod1, prod2) => (prod1.price < prod2.price ? -1 : 1));
    this.inventoryChange.emit(this.inventory);
   }
   descendingCalories(): void {
    this.inventory.sort((prod1, prod2) => (prod1.calories > prod2.calories ? -1 : 1));
    this.inventoryChange.emit(this.inventory);
   }
   ascendingCalories(): void {
    this.inventory.sort((prod1, prod2) => (prod1.calories < prod2.calories ? -1 : 1));
    this.inventoryChange.emit(this.inventory);
   }
   amountInput(value: string): void {
    this.currAmount = +value;
  }
  searchCalorieLessThanOrEqual(value: string): void {
    const number = +value;
    this.productService.searchProductsByCalories(number)
       .subscribe(inventory => {
        this.updatedInventory = inventory;
        this.inventoryChange.emit(this.updatedInventory);
      });
  }
  submit(): void {
    const details = this.searchCalories.getRawValue();
    const startingCal = details.startingCal;
    const endingCal = details.endingCal;
    this.updatedInventory = this.inventory;
    this.updatedInventory = this.updatedInventory.filter(product => product.calories >= startingCal && product.calories <=endingCal);
    this.inventoryChange.emit(this.updatedInventory);
  }
 }
