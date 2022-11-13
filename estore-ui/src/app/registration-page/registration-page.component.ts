import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Role } from '../role';
import { RoleService } from '../role.service';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, FormArray, Validators, Form, FormControl, AbstractControl } from "@angular/forms";
@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit {
  createdUsername: string = '';
  roleParam: Set<Role> = new Set;
  sentUser!:User;
  userForm!:FormGroup;

  constructor(public formBuilder: FormBuilder, private userService: UserService, private roleService: RoleService, public router: Router) { }
  ngOnInit(): void {
    this.userForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(20)]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(40), Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,24}$')]]
    })
  }
  get f(): { [key: string]: AbstractControl } {
    return this.userForm.controls;
  }
  submitCreateUserRequest(username: string, password:string): void {
    const newRole = new Role(2,"user");
    this.roleParam.add(newRole);
    const sentUser = new User(username, password, this.roleParam);
    console.log(this.roleParam);
    this.userService.createUser(sentUser).subscribe(user => {
      this.createdUsername = user.username;
      this.router.navigateByUrl("/login")
    });
  }
  submit():void {
    if(this.userForm.valid) {
      const credentials = this.userForm.getRawValue();
      const username = credentials.username;
      const password = credentials.password;
      if(username && password) {
        this.submitCreateUserRequest(username, password)
      }
    } else {
      alert("invalid form");
    }
    this.roleParam.clear();
  }
}
