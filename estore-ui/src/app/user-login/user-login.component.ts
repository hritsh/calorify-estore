/**
 * SWEN 261
 * Huser-login.components.ts
 *
 * This component handles the action of logging in for the {@linkplain User user}
 *
 * Contributors: Team-E
 */

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { UserService } from '../user.service';
import { User } from '../user';
import { LoginService } from '../login.service';
import { jwtRequest } from '../jwtRequest';
import jwt_decode from "jwt-decode";


@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit {
  jwtToken!: any;
  user!: User;
  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private location: Location,
    public router: Router,
    private loginService: LoginService
  ) {
  }

  /**
   * The initialization of this component
   */
  ngOnInit(): void {
  }

  /**
   * Returns the current {@linkplain User user} to the previous page
   */
  goBack(): void {
    this.location.back();
  }

  /**
   * The action of logging in
   * This methods gets the input in the login textbox and uses that to determine whether or not to proceed
   * To the admin page or the customer's page
   */
  login(username: string, password:string): void {
    if(username && password) {
      const sentLoginRequest = new jwtRequest(username, password);
      this.loginService.login(sentLoginRequest).subscribe(response => {
        this.jwtToken = jwt_decode(response.jwtToken);
        this.loginService.setSession(this.jwtToken, response.jwtToken);
        this.user = response.user;
        if(this.user.username == "admin") {
          this.router.navigateByUrl("/admin-store");
        } else {
          this.router.navigateByUrl("/user-store/"+this.user.username);
        }
      });
    }
  }
}
