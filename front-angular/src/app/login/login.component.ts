import { Router } from '@angular/router';
import { MatDialog } from '@angular/material';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit, Inject } from '@angular/core';

import { ResetPassComponent } from '../reset-pass/reset-pass.component';
import { MessageModalComponent } from '../message-modal/message-modal.component';

import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';
import { SnackBarService } from '../services/snack-bar.service';
import { ValidatorService } from '../services/validator.service';
import { NavigationService } from '../services/navigation.service';

import { User } from '../model/User';
import { reject } from 'q';
import { resolve } from 'url';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public errorMessage: string = '';
  public employeeNumber: string = '';
  public password: string = '';
  public hide: boolean;

  constructor(
    public router: Router,
    public dialog: MatDialog,
    public authService: AuthService,
    public snackBarService: SnackBarService,
    private nav: NavigationService,
    private http: HttpService,
    private valid: ValidatorService,
  ) {
    this.nav.hide();
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
      this.errorMessage = '入力必須項目が未入力です。';
    }
  }

  openDialog(): void {
    let data = {
        employeeNumber: '',
        mailaddress: '',
        password: 'DUMMY',
    }
    let dialogRef = this.dialog.open(ResetPassComponent, {
      width: '400px',
      data: { user: data },
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.http.postResetPass('accounts/usercheck', data).subscribe(success => {
          let mes = '';
          mes = success.message;
          let dialogRef = this.dialog.open(MessageModalComponent, {
            data: {
              message: mes
            }
          });
        }, error => {
          let dialogRef = this.dialog.open(MessageModalComponent, {
            data: {
              message: '入力された社員番号とメールアドレスが不正です。'
            }
          });
        });
      }
    });
  }

  login() {
    this.authService.login(this.employeeNumber, this.password, () => {
      if (this.authService.isLoggedIn()) {
        this.snackBarService.getUpdateId();
        let redirect = this.authService.redirectUrl ? this.authService.redirectUrl : 'top';
        this.router.navigate([redirect]);
        this.snackBarService.openSnackBar('ログインしました', '');
      } else {
        this.errorMessage = this.authService.message;
      }
    });
  }
}
