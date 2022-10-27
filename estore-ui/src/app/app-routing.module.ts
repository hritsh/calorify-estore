/**
 * SWEN 261
 * Routes the different components of the Calorify store.
 *
 * Contributors: Team-E
 */

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';

const routes: Routes = [
  //{ path: 'login', component: UserLoginComponent },
  //{ path: 'user-store/:username', component: UserStoreComponent },
  //{ path: 'admin-store', component: InventoryComponent },
  //{ path: 'product-details/:id', component: ProductDetailsComponent },
  { path: 'user-store/:username/shopping-cart', component: ShoppingCartComponent },
  //{ path: 'user-product-view/:username/:id', component: UserProductViewComponent }
]

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
