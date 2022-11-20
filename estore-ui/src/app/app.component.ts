import { Component } from '@angular/core';
import { LoginService } from './login.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Calorify';
  constructor(public loginService: LoginService) { }
  username = localStorage.getItem('sub')!;
  isLoggedIn(): boolean {
    this.username = localStorage.getItem('sub')!;
    return this.loginService.isLoggedIn();
  }
}
