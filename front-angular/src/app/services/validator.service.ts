import { Injectable } from '@angular/core';

@Injectable()
export class ValidatorService {

  constructor() { }

  empty(input: any): boolean {
    let bool: boolean = false;
    for (let i of input) {
      i = i.replace(/\s+/g, "");
      console.log(i);
      bool = bool || (i == "")
    }
    return bool;
  }

  kana(input: any): boolean {
    let bool: boolean = false;
    console.log(input);
    bool = (input[2].match(/^[\u30a1-\u30f6]+$/)) ? true : false;
    bool = (input[3].match(/^[\u30a1-\u30f6]+$/)) ? true : false;
    return bool;
  }

}
