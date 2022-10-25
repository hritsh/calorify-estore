import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InventoryPageComponent } from './inventory-page/inventory-page.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { RegistrationPageComponent } from './registration-page/registration-page.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { ProductListingComponent } from './product-listing/product-listing.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { CustomerCheckoutComponent } from './customer-checkout/customer-checkout.component';
import { ProductSearchComponent } from './product-search/product-search.component';
import { LandingPageComponent } from './landing-page/landing-page.component';

@NgModule({
  declarations: [
    AppComponent,
    InventoryPageComponent,
    ProductDetailsComponent,
    RegistrationPageComponent,
    LoginPageComponent,
    ProductListingComponent,
    ShoppingCartComponent,
    CustomerCheckoutComponent,
    ProductSearchComponent,
    LandingPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
