/**
 * SWEN 261
 * Services the product class of the Calorify store.
 *
 * Contributors: Team-E
 */

import { Injectable } from '@angular/core';
import { Product } from './product';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private productsURL = 'http://localhost:8080/products';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  /**
   * Retrieves the inventory from the backend
   *
   * @returns a list of {@linkplain Product products} that represents the inventory
   * of this store
   */
  getProducts(): Observable<Product[]> {
    const url = `${this.productsURL}`
    return this.http.get<Product[]>(url, this.httpOptions);
  }

 /**
   * sends information of the {@linkplain Product product} to be deleted from the inventory to the backend
   *
   * @param id the id that is associated with the to be deleted {@link Product product}
   *
   * @returns The {@link Product product} that was deleted
   */
  deleteProduct(id: number): Observable<Product> {
    const url = `${this.productsURL}/${id}`;
    return this.http.delete<Product>(url, this.httpOptions);
  }

 /**
   * Sends a {@linkplain Product product} back to the backend in order to update it's details
   *
   * @param product The {@link Product product} that is to be updated
   *
   * @returns an observable that is created from observing the return state of the put request
   *
   */
  updateProduct(product: Product): Observable<any> {
    const url = `${this.productsURL}`
    return this.http.put(url, product, this.httpOptions);
  }


