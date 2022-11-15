/**
 * SWEN 261
 * Services the user class of the Calorify store.
 *
 * Contributors: Team-E
 */
import { Injectable } from '@angular/core';
import { User } from './user';
import { UserDetails } from './userDetails';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Role } from './role';
@Injectable({
  providedIn: 'root'
})
export class UserService {
  private usersURL = 'http://localhost:8080/users';
  private roles:Role[] = [];
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) {}
  generateRoleArray(role:Set<Role>): Role[] {
    for(let entry of role) {
      this.roles.push(entry);
    }
    return this.roles;
  }
  /**
  * Checks to see if a {@linkplain User user} already exists by sending a username to the backend
  * @param username the username of the {@link User user} to check for
  * @returns a boolean indicating if the {@link User user} exists
  */
  createUser(user: User): Observable<User> {
    const url = `${this.usersURL}`;
    console.log(user);
    return this.http.post<User>(url, {
      "username": user.username,
      "password": user.password,
      "role": this.generateRoleArray(user.role)
    }, this.httpOptions);
  }
  userExists(username: string): Observable<boolean> {
    const url = `${this.usersURL}/${username}`;
    return this.http.get<boolean>(url);
  }
  updateUserDetails(userDetails: UserDetails): Observable<UserDetails> {
    const url = `${this.usersURL}`
    return this.http.put<UserDetails>(url, {
      "username": userDetails.username,
      "firstName": userDetails.firstName,
      "lastName":userDetails.lastName,
      "gender": userDetails.gender,
      "height": userDetails.height,
      "weight": userDetails.weight,
      "age": userDetails.age
    });
  }
  /**
  * Retrieves a {@linkplain User user} by sending a string to the backend
  * @param username the username to use to search for a {@link User user}
  * @returns the retrieved {@link User user}
  */
  getUser(username: string): Observable<User> {
    const url = `${this.usersURL}/${username}`;
    return this.http.get<User>(url);
  }
  getUserDetails(username: string): Observable<UserDetails> {
    const url = `${this.usersURL}/${username}`;
    return this.http.get<UserDetails>(url);
  }

}
