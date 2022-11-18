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
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RegistrationPageComponent } from './registration-page/registration-page.component'; // <-- NgModel lives here
import { InventoryComponent } from './inventory/inventory.component';
import { ProductSearchComponent } from './product-search/product-search.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { UserStoreComponent } from './user-store/user-store.component';
import { UserProductViewComponent } from './user-product-view/user-product-view.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AuthInterceptor } from './auth-interceptor';
import { LogoutComponent } from './logout/logout.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ViewUserProfileComponent } from './view-user-profile/view-user-profile.component';
import { EditUserProfileComponent } from './edit-user-profile/edit-user-profile.component';
import { SaladMakerComponent } from './salad-maker/salad-maker.component';
import { BMIHomeComponent } from './bmi-home/bmi-home.component';
import { BMIResultComponent } from './bmi-result/bmi-result.component';

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
    UserProductViewComponent,
    PageNotFoundComponent,
    LogoutComponent,
    SaladMakerComponent,
    BMIHomeComponent,
    BMIResultComponent,
    UserProfileComponent,
    ViewUserProfileComponent,
    EditUserProfileComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    ReactiveFormsModule,
    FormsModule,
    AppRoutingModule,
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})

export class AppModule { }
