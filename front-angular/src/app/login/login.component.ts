import { Component, OnInit, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatDialog } from '@angular/material';

import { ResetPassComponent } from '../reset-pass/reset-pass.component';
import { MessageModalComponent } from '../message-modal/message-modal.component'

import { NavigationService } from '../services/navigation.service';
import { AuthService } from '../services/auth.service';
import { ValidatorService } from '../services/validator.service';
import { SnackBarService } from '../services/snack-bar.service'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  errorMessage: string = '';

  employeeNumber: string = '';
  password: string = 'pass2017';

  private message: string;
  public hide: boolean;

  private user: any = {
    employeeNumber: '',
    mailaddress: '',
    password: '',
  }

  constructor(
    @Inject('hostname') private hostname: string,
    public authService: AuthService,
    public router: Router,
    public dialog: MatDialog,
    public snackBar: SnackBarService,
    private nav: NavigationService,
    private valid: ValidatorService,
    private http: HttpClient
  ) {
    this.nav.hide();
  }

  openDialog(): void {
    console.log('ok')
    let dialogRef = this.dialog.open(ResetPassComponent, {
      width: '400px',
      data: { user: this.user },
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result != null) {
        this.user.password = 'dummypass'
        this.http.post(this.hostname + "accounts/usercheck", this.user, { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
          json => {
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
    if (!this.valid.empty(this.employeeNumber) && !this.valid.empty(this.password)) {
      this.login();
    } else {
      this.errorMessage = "入力必須項目が未入力です。";
    }
  }

  login() {
    this.authService.login(this.http, this.hostname + 'login', this.employeeNumber, this.password, () => {
      if (this.authService.isLoggedIn()) {
        let redirect = this.authService.redirectUrl ? this.authService.redirectUrl : 'top';
        this.router.navigate([redirect]);
        this.snackBar.openLoginSnackBar();
      } else {
        this.errorMessage = this.authService.message;
        console.log(this.errorMessage);
      }
    });
  }
}
