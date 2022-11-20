import { Component } from '@angular/core';
import { LoginService } from './login.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Calorify E-Store';
  constructor(public loginService: LoginService) { }
  isLoggedIn(): boolean {
    return this.loginService.isLoggedIn();
  }
  username = localStorage.getItem('sub')!;
}
