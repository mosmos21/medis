import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs/Subject';

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
    return this.http.get(this.hostname + url, { withCredentials: true, headers: this.authService.headerAddToken() });
  }

  getWithPromise(url: string): any {
    return this.get(url).toPromise();
  }

  post(url: string, data: Object): any {
    this.http.post(this.hostname + url, data, { withCredentials: true, headers: this.authService.headerAddToken() });
  }

  postWithPromise(url: string, data: Object): any {
    return this.post(url, data);
  }

  put(url: string, data: Object): any {
    this.http.put(this.hostname + url, data, { withCredentials: true, headers: this.authService.headerAddToken() });
  }

  putWithPromise(url: string, data: Object): any {
    return this.put(url, data);
  }
}