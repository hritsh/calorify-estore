import { Role } from "./role";

export class UserDetails {
  username!: string;
  firstName!: string;
  lastName!: string;
  gender!: string;
  height!: number;
  weight!:number;
  age!:number;

  constructor(username:string,
    firstName: string, lastName: string, gender: string, height: number,
    weight:number, age:number) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.height = height;
    this.weight = weight;
    this.age = age;
  }
}
