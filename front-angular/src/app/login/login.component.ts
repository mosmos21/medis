import { Component, OnInit, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatDialog } from '@angular/material';

import { ResetPassComponent } from '../reset-pass/reset-pass.component';
import { MessageModalComponent } from '../message-modal/message-modal.component';

import { NavigationService } from '../services/navigation.service';
import { AuthService } from '../services/auth.service';
import { ValidatorService } from '../services/validator.service';
import { SnackBarService } from '../services/snack-bar.service';
import { User } from '../model/User';

import { reject } from 'q';
import { resolve } from 'url';
import { HttpService } from '../services/http.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public errorMessage: string = '';
  public employeeNumber: string = '';
  public password: string = 'pass2017';
  public hide: boolean;

  private message: string;
  private user: User;

  constructor(
    public authService: AuthService,
    public router: Router,
    public dialog: MatDialog,
    public snackBarService: SnackBarService,
    private nav: NavigationService,
    private valid: ValidatorService,
    private http: HttpService,
  ) {
    this.nav.hide();
  }

  openDialog(): void {
    let dialogRef = this.dialog.open(ResetPassComponent, {
      width: '400px',
      data: { user: this.user },
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result != null) {
        this.http.post("accounts/usercheck", this.user).subscribe(
          success => {
            this.message = 'パスワード再設定用メールを送信しました。'
            let dialogRef = this.dialog.open(MessageModalComponent, {
              data: {
                message: this.message
              }
            });
          },
          error => {
            console.log(error);
            this.message = '入力された社員番号とメールアドレスが不正です。'
            let dialogRef = this.dialog.open(MessageModalComponent, {
              data: {
                message: this.message
              }
            });
          }
        );
      }
    });
  }

  ngOnInit() { }

  onSubmit() {
    let loginUser = [
      this.employeeNumber,
      this.password,
    ]
    if (!this.valid.empty(loginUser)) {
      this.login();
    } else {
      this.errorMessage = "入力必須項目が未入力です。";
    }
  }

  login() {
    this.authService.login(this.employeeNumber, this.password, () => {
      if (this.authService.isLoggedIn()) {
        let redirect = this.authService.redirectUrl ? this.authService.redirectUrl : 'top';
        this.router.navigate([redirect]);
        this.snackBarService.openSnackBar("ログインしました", "");
      } else {
        this.errorMessage = this.authService.message;
      }
    });
  }
}
