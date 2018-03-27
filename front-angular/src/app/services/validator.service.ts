import { Injectable } from '@angular/core';

@Injectable()
export class ValidatorService {

  constructor() { }

  empty(input: any): boolean {
    let bool: boolean = false;
    for (let i of input) {
      if (i == null) return true;
      i = i.replace(/\s+/g, "");
      bool = bool || (i == "")
    }
    return bool;
  }

  employeeNumber(employeeNumber: String): boolean {
    let bool: boolean = false;
    bool = (employeeNumber.match(/^[a-zA-Z0-9]{4,9}$/)) ? false : true;
    return bool;
  }

  kana(lastName: String, firstName: String): boolean {
    let bool: boolean = false;
    bool = (lastName.match(/^[\u30a1-\u30f6]+$/)) && (firstName.match(/^[\u30a1-\u30f6]+$/)) ? false : true;
    return bool;
  }

  mail(mail: String): boolean {
    let bool: boolean = false;
    bool = (mail.match(/^[^0-9][a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@\[?([\d\w\.-]+)]?$/)) ? false : true;
    return bool;
  }
}
