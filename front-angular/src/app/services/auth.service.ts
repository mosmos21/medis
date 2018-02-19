import { Injectable } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/delay';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AuthService {


  isLoggedIn: boolean = false;
  user: any;

  // store the URL so we can redirect after logging in
  redirectUrl: string;

  message: string;

  login(http: HttpClient, url: string, employeeNumber: string, password: string, callback: any): void {
    var data = {
      employeeNumber: employeeNumber,
      password: password
    }
    
    http.post(url, data).subscribe(
      success => {
        this.user = success;
        console.log(this.user);
        this.isLoggedIn = true;
        callback();
      },
      error => {
        this.message = "社員番号とパスワードの組み合わせが\n異なります";
        callback();
      }
    );
  }

  logout(): void {
    this.isLoggedIn = false;
  }
}