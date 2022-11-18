import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import { UserDetails } from '../userDetails';

@Component({
  selector: 'app-view-user-profile',
  templateUrl: './view-user-profile.component.html',
  styleUrls: ['./view-user-profile.component.css']
})
export class ViewUserProfileComponent implements OnInit {
  userDetails!: UserDetails;
  constructor(private userService: UserService, public router: Router) { }

  ngOnInit(): void {
    this.getUserDetails();
  }

  getUserDetails(): UserDetails {
    const username = localStorage.getItem('sub')!;
     var token = (localStorage.getItem('token'));
    if(username !=null && token!=null) {
      this.userService.getUserDetails(username)
      .subscribe(userDetails => this.userDetails = userDetails)
    }
     return this.userDetails!;
  }
  goToEditUserProfile():void {
    this.router.navigateByUrl('/edit-user-profile');
  }
}
