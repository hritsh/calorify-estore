import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit {
  user: User = {
    username: '',
    password: ''
  };
  constructor(private userService: UserService ) { }

  ngOnInit(): void {
  }
  submit(username: string, password: string): void {
    username = username.trim();
    password = password.trim();
    this.userService.createUser({username, password} as User).subscribe(user => {
      this.user.password = user.password;
      this.user.username = user.username;
    });
  }
}
