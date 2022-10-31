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

