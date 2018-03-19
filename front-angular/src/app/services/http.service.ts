import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

import { AuthService } from '../services/auth.service';
import { ErrorService } from './error.service';

@Injectable()
export class HttpService {

  constructor(
    @Inject('hostname') private hostname: string,
    private http: HttpClient,
    private authService: AuthService,
    private errorService: ErrorService,
  ) { }

  get(url: string): any {
    if(!this.authService.isLoggedIn()) {
      return Observable.of(false);
    }
    return this.http.get(this.hostname + url, { withCredentials: true, headers: this.authService.headerAddToken() });
  }

  getWithPromise(url: string): any {
    return this.get(url).toPromise();
  }

  post(url: string, data: Object): any {
    if(!this.authService.isLoggedIn()) {
      return Observable.of(false);
    }
    return this.http.post(this.hostname + url, data, { withCredentials: true, headers: this.authService.headerAddToken() });
  }

  postWithPromise(url: string, data: Object): any {
    return this.post(url, data);
  }

  put(url: string, data: Object): any {
    if(!this.authService.isLoggedIn()) {
      return Observable.of(false);
    }
    return this.http.put(this.hostname + url, data, { withCredentials: true, headers: this.authService.headerAddToken() });
  }

  putWithPromise(url: string, data: Object): any {
    return this.put(url, data);
  }
}