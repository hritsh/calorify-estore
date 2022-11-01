import { Injectable } from '@angular/core';
import { User } from './user';
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

  constructor(private http: HttpClient) {
  }
  generateRoleArray(role:Set<Role>): Role[] {
    for(let entry of role) {
      this.roles.push(entry);
    }
    return this.roles;
  }
  createUser(user: User): Observable<User> {
    const url = `${this.usersURL}`;
    console.log(user);
    return this.http.post<User>(url, {
      "username": user.username,
      "password": user.password,
      "role": this.generateRoleArray(user.role)
    }, this.httpOptions);
  }
}
