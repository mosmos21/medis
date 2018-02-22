import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

import { NavigationService } from '../services/navigation.service';

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
  private password = "";
  private passwordCheck = "";
  private errorMessage = "";

  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    @Inject('hostname') private hostname: string,
    private nav: NavigationService,
  ) {
    nav.hide();
  }

  sendNewPassword() {
    if(this.password == this.passwordCheck) {
      this.errorMessage = "";
      this.user["password"] = this.password;
      this.http.post(this.hostname + "accounts/" + this.user["employeeNumber"], this.user).subscribe(
        json => {
          // TODO
        },
        error => {
          // TODO
        }
      );
    } else {
      this.errorMessage = "新しいパスワードと新しいパスワード（確認）が異なります。";
    }
  }

  ngOnInit() {
    this.route.queryParams.subscribe(qParams => {
      this.http.post(this.hostname + "accounts/keycheck", qParams).subscribe(
        json => {
          this.user["employeeNumber"] = json["employeeNumber"];
          this.user["mailadress"] = json["mailadress"];
        },
        error => {
          // TODO
        }
      );
    })
  }

}
