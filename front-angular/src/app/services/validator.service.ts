import { Injectable } from '@angular/core';

@Injectable()
export class ValidatorService {

  constructor() { }

  empty(input: string): boolean {
    return input == "";
  }

}
