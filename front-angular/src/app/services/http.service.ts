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
    if (!this.authService.isLoggedIn()) {
      return Observable.of(false);
    }
    return this.http.get(this.hostname + url, { withCredentials: true, headers: this.authService.headerAddToken() });
  }

  getWithPromise(url: string): any {
    return this.get(url).toPromise();
  }

  post(url: string, data: Object): any {
    if (!this.authService.isLoggedIn()) {
      return Observable.of(false);
    }
    return this.http.post(this.hostname + url, data, { withCredentials: true, headers: this.authService.headerAddToken(), responseType: 'text' });
  }

  postWithPromise(url: string, data: Object): any {
    return this.post(url, data).toPromise();
  }

  put(url: string, data: Object): any {
    if (!this.authService.isLoggedIn()) {
      return Observable.of(false);
    }
    return this.http.put(this.hostname + url, data, { withCredentials: true, headers: this.authService.headerAddToken(), responseType: 'text' });
  }

  putWithPromise(url: string, data: Object): any {
    return this.put(url, data).toPromise();
  }

  delete(url: string): any {
    return this.http.delete(this.hostname + url, { withCredentials: true, headers: this.authService.headerAddToken() });
  }

  postIcon(file: File) {
    let formdata: FormData = new FormData();
    formdata.append('file', file);
    this.http.post(this.hostname + "icon", formdata, { withCredentials: true, headers: this.authService.headerMultipart(), responseType: 'text' }).subscribe(id => { }, error => {
    });
  }

  postResetPass(url: string, data: Object): any {
    return this.http.post(this.hostname + url, data, { withCredentials: true });
  }

  getHostname(): string {
    return this.hostname;
  }
}