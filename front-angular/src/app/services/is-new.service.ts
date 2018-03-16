import { Injectable } from '@angular/core';

@Injectable()
export class IsNewService {

  constructor() { }

  checkNew(time: number): boolean {
    let updateDate = time;
    let currentDate = new Date().getTime();
    if (currentDate - updateDate < 86400000) {
      return true;
    }
    return false;
  }
}
