import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Role } from '../role';
import { RoleService } from '../role.service';
import { map } from 'rxjs';
@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit {
  roles: Role[] = [];
  roleMap = new Map();
  roleNo: number = 1;
  createdUsername: string = '';
  roleParam: Set<Role> = new Set;
  constructor(private userService: UserService, private roleService: RoleService ) { }

  ngOnInit(): void {
    this.roleService.getRoles().subscribe(response => {
      this.roles = response;
    })
  }
  getCheckBoxValue(event: any) {
    if(event.target?.checked){
      if(event.target?.name == "user")
        this.roleNo=2;
      else if(event.target?.name == "admin")
        this.roleNo=1;
      else if(event.target?.name == "customer")
        this.roleNo = 3;
      this.roleMap.set(event.target?.name, this.roleNo)
    } else {
      this.roleMap.delete(event.target?.name);
    }
    console.log(this.roleMap);
  }
  submitCreateUserRequest(username: string, password:string): void {
    for(let [key,value] of this.roleMap.entries()) {
      this.roleParam.add(new Role(value,key));
      //this.roles.push(new Role(value, key));
    }
    const sentUser = new User(username, password, this.roleParam);
    console.log(this.roleParam);
    //check code here
    this.userService.createUser(sentUser).subscribe(user => {
      this.createdUsername = user.username;
    });
  }
  submit(username: string, password: string): void {
    this.roleParam.clear();
    username = username.trim();
    password = password.trim();
    this.submitCreateUserRequest(username, password)

  }
}
