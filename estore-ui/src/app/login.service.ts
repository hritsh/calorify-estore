/**
 * SWEN 261
 * Services the user class of the Calorify store.
 *
 * Contributors: Team-E
 */
import { Injectable } from '@angular/core';
import { User } from './user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { jwtRequest } from './jwtRequest';
import { jwtResponse } from './jwtResponse';
import * as moment from "moment";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private loginURL = 'http://localhost:8080/auth/login';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(private http: HttpClient) { }
  /**
  * Checks to see if a {@linkplain User user} already exists by sending a username to the backend
  * @param username the username of the {@link User user} to check for
  * @returns a boolean indicating if the {@link User user} exists
  */
  login(jwtRequest: jwtRequest): Observable<jwtResponse> {
    const url = `${this.loginURL}`;
    console.log(jwtRequest);
    return this.http.post<jwtResponse>(url, {
      "username": jwtRequest.username,
      "password": jwtRequest.password
    }, this.httpOptions);
  }
  setSession(jwtTokenObj: any, jwtTokenString: string) {
    const expiresAt = moment().add(jwtTokenObj.exp,'second');
    localStorage.setItem('token', jwtTokenString);
    console.log(jwtTokenString);
    localStorage.setItem('sub', jwtTokenObj.sub);
    localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf()) );
  }
  logout() {
    localStorage.removeItem("sub");
    localStorage.removeItem("expires_at");
  }
  public isLoggedIn() {
    return moment().isBefore(this.getExpiration());
  }
  isLoggedOut() {
    return !this.isLoggedIn();
  }
  getExpiration() {
    const expiration: any = localStorage.getItem("expires_at");
    const expiresAt = JSON.parse(expiration);
    return moment(expiresAt);
  }
}
