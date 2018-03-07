import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

import { AuthService } from '../services/auth.service';
import { NavigationService } from '../services/navigation.service';
import { ErrorService } from '../services/error.service';
import { MatDialog } from '@angular/material';
import { ConfirmationComponent } from '../confirmation/confirmation.component';

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
    public dialog: MatDialog
  ) {
    nav.hide();
  }

  ngOnInit() {
    this.route.queryParams.subscribe(qParams => {
      let params = {
        secret : qParams["secret"]
      }
      this.http.post(this.hostname + "accounts/keycheck", params).subscribe(
        json => {
          if(json["result"] == "NG") {
            let dialogRef = this.dialog.open(ConfirmationComponent, {
              data: {
                message: json["message"]
              }
            });
            dialogRef.afterClosed().subscribe(result => {
              this.router.navigate(['/login']);
            });
          } else {
            this.user["employeeNumber"] = json["employeeNumber"];
            this.user["mailaddress"] = json["mailaddress"];
          }
        },
        error => {
          this.errorService.errorPath(error.status)
        }
      );
    })
  }

  sendNewPassword() {
    if (this.password == this.passwordCheck) {
      this.errorMessage = "";
      this.user["password"] = this.password;
      this.http.post(this.hostname + "accounts/reset", this.user, { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
        json => {
          let dialogRef = this.dialog.open(ConfirmationComponent, {
            data: {
              message: "パスワードの初期化が完了しました"
            }
          });
        },
        error => {
          this.errorService.errorPath(error.status)
        }
      );
    } else {
      this.errorMessage = "新しいパスワードと新しいパスワード（確認）が異なります。";
    }
  }


}
