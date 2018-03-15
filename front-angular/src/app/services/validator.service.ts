import { Injectable } from '@angular/core';

@Injectable()
export class ValidatorService {

  constructor() { }

  empty(input: any): boolean {
    let bool: boolean = false;
    for(let i of input) {
      bool = bool || (i == "")
    }
    return bool;
  }

}
