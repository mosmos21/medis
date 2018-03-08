import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/delay';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tokenNotExpired } from 'angular2-jwt';
import { RequestOptions } from '@angular/http'
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

declare var auth0: any;

@Injectable()
export class AuthService {

  headers = new HttpHeaders;
  private options = new RequestOptions;
  user: any;
  userdetail: any = {
    employeeNumber: "",
    firstName: "",
    firstNamePhonetic: "",
    icon: "",
    lastName: "",
    lastNamePhonetic: "",
    mailaddress: "",
  }

  // store the URL so we can redirect after logging in
  redirectUrl: string;

  message: string;

  constructor(
    private router: Router,
    private cookieService: CookieService,
    @Inject('hostname') private hostname: string,
  ) { }

  login(http: HttpClient, url: string, employeeNumber: string, password: string, callback: any): void {
    var data = {
      employeeNumber: employeeNumber,
      password: password
    }
    http.post(url, data, { withCredentials: true }).subscribe(
      success => {
        this.user = success;
        this.getUserDetail(http);
        console.log(this.user);
        localStorage.setItem('token', this.cookieService.get('XSRF-TOKEN'))
        if (this.user.authorityId == "a0000000000") {
          this.redirectUrl = "admin/template"
        } else {
          this.redirectUrl = "top"
        }
        callback();
      },
      error => {
        console.log(error)
        localStorage.removeItem('token')
        if (error.status == '403') {
          this.message = "社員番号とパスワードの\n組み合わせが異なります";
        } else {
          this.message = "通信に失敗しました。";
        }
        callback();
      }
    );
  }

  logout(http: HttpClient, url: string): void {
    http.get(url, { withCredentials: true, headers: this.headerAddToken() }).subscribe(
      success => {
        localStorage.removeItem('token')
        this.user = "";
        this.redirectUrl = "";
        this.router.navigate(['/login']);
      },
      error => {
        localStorage.removeItem('token')
        this.router.navigate(['/login']);
      }
    )
  }

  isLoggedIn(): boolean {
    return this.cookieService.get('XSRF-TOKEN') == localStorage.getItem('token')
  }

  headerAddToken(): HttpHeaders {
    this.headers = this.headers.set('X-XSRF-TOKEN', this.cookieService.get('XSRF-TOKEN'));
    this.headers = this.headers.set('Content-Type', 'application/json');
    return this.headers
  }

  getUserDetail(http: HttpClient) {
    http.get(this.hostname + 'settings/me', { withCredentials: true, headers: this.headerAddToken() }).subscribe(
      json => {
        this.userdetail = json;
        console.log(this.userdetail);
      },
      error => {
      }
    );
  }
}