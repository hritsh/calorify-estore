import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Role } from './role';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private rolesURL = 'http://localhost:8080/roles';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) {}

  getRoles(): Observable<Role[]> {
    const url = `${this.rolesURL}`
    return this.http.get<Role[]>(url, this.httpOptions);
  }

}
