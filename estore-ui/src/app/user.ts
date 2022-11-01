import { Role } from "./role";

export class User {
  username!: string;
  password!: string;
  role!: Set<Role>;

  constructor(username:string, password:string, role:Set<Role>) {
    this.username = username;
    this.password = password;
    this.role = role;
  }
}
