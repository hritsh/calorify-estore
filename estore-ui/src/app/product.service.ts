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
