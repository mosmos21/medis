import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

import { AuthService } from '../services/auth.service';
import { NavigationService } from '../services/navigation.service';
import { ErrorService } from '../services/error.service';

@Component({
  selector: 'app-new-password',
  templateUrl: './new-password.component.html',
  styleUrls: ['./new-password.component.css']
})
export class NewPasswordComponent implements OnInit {

  private user = {
    employeeNumber: "",
    mailaddress: "",
    password: "",
  };
  public password = "";
  public passwordCheck = "";
  public errorMessage = "";
  public hide: boolean;
  public hide_check: boolean;

  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    @Inject('hostname') private hostname: string,
    private authService: AuthService,
    private errorService: ErrorService,
    private nav: NavigationService,
  ) {
    nav.hide();
  }

  sendNewPassword() {
    if (this.password == this.passwordCheck) {
      this.errorMessage = "";
      this.user["password"] = this.password;
      this.http.post(this.hostname + "accounts/" + this.user["employeeNumber"], this.user, { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
        json => {
          // TODO
        },
        error => {
          this.errorService.errorPath(error.status)
        }
      );
    } else {
      this.errorMessage = "新しいパスワードと新しいパスワード（確認）が異なります。";
    }
  }

  ngOnInit() {
    this.route.queryParams.subscribe(qParams => {
      this.http.post(this.hostname + "accounts/keycheck", qParams, { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
        json => {
          this.user["employeeNumber"] = json["employeeNumber"];
          this.user["mailadress"] = json["mailadress"];
        },
        error => {
          this.errorService.errorPath(error.status)
        }
      );
    })
  }

}
