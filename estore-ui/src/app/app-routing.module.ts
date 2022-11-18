/**
 * SWEN 261
 * Routes the different components of the Calorify store.
 *
 * Contributors: Team-E
 */

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { RegistrationPageComponent } from './registration-page/registration-page.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { UserStoreComponent } from './user-store/user-store.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { InventoryComponent } from './inventory/inventory.component';
import { UserProductViewComponent } from './user-product-view/user-product-view.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AuthGuard } from './auth-guard';
import { SaladMakerComponent } from './salad-maker/salad-maker.component';

const routes: Routes = [
  { path: 'login', component: UserLoginComponent },
  { path: 'register', component: RegistrationPageComponent },
  { path: 'user-store', component: UserStoreComponent },
  { path: 'admin-store', component: InventoryComponent, canActivate: [AuthGuard] },
  { path: 'product-details/:id', component: ProductDetailsComponent, canActivate: [AuthGuard] },
  { path: 'user-store/shopping-cart', component: ShoppingCartComponent, canActivate: [AuthGuard] },
  { path: 'user-product-view/:id', component: UserProductViewComponent },
  { path: 'user-store/:username/shopping-cart', component: ShoppingCartComponent, canActivate: [AuthGuard] },
  { path: 'user-product-view/:username/:id', component: UserProductViewComponent },
  { path: 'salad-maker/:username', component: SaladMakerComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
]

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }