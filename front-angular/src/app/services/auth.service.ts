import { Injectable, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { RequestOptions } from '@angular/http'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs/Observable';
import { tokenNotExpired } from 'angular2-jwt';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/delay';
import { ErrorService } from './error.service';
import { User } from '../model/User';
import { UserDetail } from '../model/UserDetail';

declare var auth0: any;

@Injectable()
export class AuthService {

  public message: string;
  public redirectUrl: string;
  public user: User;
  public userdetail: UserDetail;

  private headers = new HttpHeaders;
  private options = new RequestOptions;

  constructor(
    @Inject('hostname') private hostname: string,
    private http: HttpClient,
    private router: Router,
    private cookieService: CookieService,
  ) {
    this.init();
  }

  init(): void {
    this.user = new User;
    this.userdetail = new UserDetail(this.hostname);
  }

  login(employeeNumber: string, password: string, callback: any): void {
    var data = {
      employeeNumber: employeeNumber,
      password: password
    }
    this.http.post(this.hostname + 'login', data, { withCredentials: true }).subscribe(
      data => {
        this.user = new User(data);
        this.getUserDetail();
        localStorage.setItem('token', this.cookieService.get('XSRF-TOKEN'))
        if (this.user["authorityId"] == "a0000000000") {
          this.redirectUrl = "admin/template"
        }
        callback();
      },
      error => {
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

  logout(): void {
    this.http.get(this.hostname + 'logout', { withCredentials: true, headers: this.headerAddToken() }).subscribe(
      success => {
        this.init();
      },
      error => {
      }
    );
    localStorage.removeItem('token')
    this.router.navigate(['/login'])
    this.redirectUrl = 'top'
  }

  errorLogout(): void {
    this.http.get(this.hostname + 'logout', { withCredentials: true, headers: this.headerAddToken() }).subscribe(
      success => {
        localStorage.removeItem('token')
        this.init();
      },
      error => {
        localStorage.removeItem('token')
      }
    );
  }

  isLoggedIn(): boolean {
    return this.cookieService.get('XSRF-TOKEN') == localStorage.getItem('token');
  }

  headerAddToken(): HttpHeaders {
    this.headers = this.headers.set('X-XSRF-TOKEN', this.cookieService.get('XSRF-TOKEN'));
    this.headers = this.headers.set('Content-Type', 'application/json');
    return this.headers
  }

  headerMultipart(): HttpHeaders {
    this.headers = this.headers.set('X-XSRF-TOKEN', this.cookieService.get('XSRF-TOKEN'));
    this.headers = this.headers.delete('Content-Type');
    return this.headers
  }

  getUserDetail(): void {
    this.http.get(this.hostname + 'settings/me', { withCredentials: true, headers: this.headerAddToken() }).subscribe(
      data => {
        this.userdetail.iconUrl = '';
        this.userdetail = new UserDetail(this.hostname, data);
      },
      error => {
      }
    );
  }

  isUserEmpty(): boolean {
    return !Object.keys(this.userdetail).length;
  }
}