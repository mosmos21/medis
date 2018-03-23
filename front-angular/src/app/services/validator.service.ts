import { Injectable } from '@angular/core';

@Injectable()
export class ValidatorService {

  constructor() { }

  empty(input: string[]): boolean {
    let bool: boolean = false;
    for (let i of input) {
      i = i.replace(/\s+/g, "");
      bool = bool || (i == "")
    }
    return bool;
  }

  kana(input: any): boolean {
    let bool: boolean = false;
    bool = (input[2].match(/^[\u30a1-\u30f6]+$/)) ? false : true;
    bool = (input[3].match(/^[\u30a1-\u30f6]+$/)) ? false : true;
    return bool;
  }

}
