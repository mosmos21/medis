import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class MsgToSidenavService {

  private msgToSidenavDataSource = new Subject<any>();
  public msgToSidenavData$ = this.msgToSidenavDataSource.asObservable();

  constructor() { }

  sendMsg() {
    this.msgToSidenavDataSource.next("msg");
  }
}
