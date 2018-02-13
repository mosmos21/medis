import { Injectable } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/delay';

@Injectable()
export class AuthService {


  isLoggedIn: boolean = false;

  // store the URL so we can redirect after logging in
  redirectUrl: string;

  message: string;

  login(employeeNumber: string, password: string): Observable<boolean> {
    if(employeeNumber == "99999" && password == "hoge"){
      return Observable.of(true).delay(1000).do(val => this.isLoggedIn = true);
    }
    this.message = "社員番号とパスワードの組み合わせが\n異なります";
    return Observable.of(false);
  }

  logout(): void {
    this.isLoggedIn = false;
  }
}