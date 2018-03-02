import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/delay';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import {CookieService} from 'ngx-cookie-service';

declare var auth0: any;

@Injectable()
export class AuthService {

  headers = new HttpHeaders;

  isLoggedIn: boolean = false;
  user: any;

  // store the URL so we can redirect after logging in
  redirectUrl: string;

  message: string;

  constructor(
    private router: Router,
    private cookieService: CookieService
  ) {}

  login(http: HttpClient, url: string, employeeNumber: string, password: string, callback: any): void {
    var data = {
      employeeNumber: employeeNumber,
      password: password
    }
    this.headers = this.headers.append('Content-Type', 'application/json');
    http.post(url, data, { withCredentials: true, headers: this.headers }).subscribe(
      success => {
        this.user = success;
        console.log(this.user);
        this.isLoggedIn = true;
        if(this.user.authorityId == "a0000000000") {
          this.redirectUrl = "admin/template"
        }
        callback();
      },
      error => {
        console.log(error)
        this.message = "社員番号とパスワードの組み合わせが\n異なります";
        callback();
      }
    );
  }

  logout(): void {
    this.isLoggedIn = false;
  }

  headerAddToken(): HttpHeaders {
    console.log(this.cookieService.get('XSRF-TOKEN'))
    this.headers = this.headers.append('X-XSRF-TOKEN', this.cookieService.get('XSRF-TOKEN'));
    this.headers = this.headers.append('Content-Type', 'application/json');
    console.log(this.headers)
    return this.headers
  }
}