import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class SearchService {

  constructor() { }

  private searchTagsDataSource = new Subject<string>();

  public searchTagsData$ = this.searchTagsDataSource.asObservable();

  sendMsg(msg: any) {
    this.searchTagsDataSource.next(msg);
  }

}
