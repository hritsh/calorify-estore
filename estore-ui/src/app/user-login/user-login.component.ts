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
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit {
  jwtToken!: any;
  user!: User;
  form!: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
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
    this.form = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(20)]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(40)]]
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.form.controls;
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
  login(): void {
    if(this.form.valid) {
      const credentials = this.form.getRawValue();
      const username = credentials.username;
      const password = credentials.password;
      if(username && password) {
        const sentLoginRequest = new jwtRequest(username, password);
        this.loginService.login(sentLoginRequest).subscribe(response => {
          this.jwtToken = jwt_decode(response.jwtToken);
          this.loginService.setSession(this.jwtToken, response.jwtToken);
          this.user = response.user;
          if(this.user.username == "admin") {
            this.router.navigateByUrl("/admin-store");
          } else {
            this.router.navigateByUrl("/user-store");
          }
        });
      }
    } else {
      alert('Error');
    }
  }
}
