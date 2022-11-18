import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  username: any = localStorage.getItem("sub");
  constructor(public router: Router) { }

  ngOnInit(): void {
  }
  goToUserProfile():void {
    this.router.navigateByUrl('/user-profile');
  }

}
