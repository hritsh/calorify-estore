import { User } from "./user";

export class jwtResponse {
  user!:User;
  jwtToken!:string;
  constructor(user:User, jwtToken:string) {
    this.user = user;
    this.jwtToken = jwtToken;
  }
}
