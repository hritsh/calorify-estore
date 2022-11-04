/**
 * SWEN 261
 * Declares the components of the Calorify store.
 *
 * Contributors: Team-E
 */

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';

import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';

import { FormsModule } from '@angular/forms';
import { RegistrationPageComponent } from './registration-page/registration-page.component'; // <-- NgModel lives here
import { InventoryComponent } from './inventory/inventory.component';
import { ProductSearchComponent } from './product-search/product-search.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { UserStoreComponent } from './user-store/user-store.component';
import { UserProductViewComponent } from './user-product-view/user-product-view.component';

@NgModule({
  declarations: [
    AppComponent,
    ShoppingCartComponent,
    RegistrationPageComponent,
    InventoryComponent,
    ProductSearchComponent,
    ProductDetailsComponent,
    UserLoginComponent,
    UserStoreComponent,
    UserProductViewComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    FormsModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
