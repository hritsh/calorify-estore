import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RoleService } from '../role.service';
import { UserService } from '../user.service';
import { UserDetails } from '../userDetails';

@Component({
  selector: 'app-edit-user-profile',
  templateUrl: './edit-user-profile.component.html',
  styleUrls: ['./edit-user-profile.component.css']
})
export class EditUserProfileComponent implements OnInit {
  editUserForm!:FormGroup
  userDetails!: UserDetails;
  constructor(public formBuilder: FormBuilder, private userService: UserService, private roleService: RoleService, public router: Router) { }

  ngOnInit(): void {
    this.editUserForm = this.formBuilder.group({
      firstName: [''],
      lastName: [''],
      gender: [''],
      height: [''],
      weight: [''],
      age: ['']
    })
    this.getUserDetails();
  }
  getUserDetails(): UserDetails {
    const username = localStorage.getItem('sub')!;
    var token = (localStorage.getItem('token'));
    if(username !=null && token!=null) {
      this.userService.getUserDetails(username)
      .subscribe(userDetails => {
        this.userDetails = userDetails;
        this.editUserForm.setValue({
          firstName: userDetails.firstName,
          lastName: userDetails.lastName,
          gender: userDetails.gender,
          height: userDetails.height,
          weight: userDetails.weight,
          age: userDetails.age
        })})
    }
     return this.userDetails!;
  }
  submit(): void {
    const details = this.editUserForm.getRawValue();
    console.log(details);
    const username = localStorage.getItem('sub')!;
    var token = (localStorage.getItem('token'));
    console.log(username)

    if(username !=null && token!=null) {
      this.userService.updateUserDetails(new UserDetails(this.userDetails.username,details.firstName, details.lastName, details.gender, details.height,details.weight,details.age))
      .subscribe(userDetails => {
        this.router.navigateByUrl('user-profile');
      })
    }
  }
}
