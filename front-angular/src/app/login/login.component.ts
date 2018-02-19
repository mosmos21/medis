import { Component, OnInit, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatDialog } from '@angular/material';

import { ResetPassComponent } from '../reset-pass/reset-pass.component';
import { MessageModalComponent } from '../message-modal/message-modal.component'

import { NavigationService } from '../services/navigation.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  errorMessage: string = '';

  employeeNumber: string = 'gu';
  password: string = 'gupass';

  private message: string;

  private user: any = {
    employeeNumber: '',
    mailadress: ''
  }

  constructor(
    @Inject('hostname') private hostname: string,
    public authService: AuthService,
    public router: Router,
    public dialog: MatDialog,
    private nav: NavigationService,
    private http: HttpClient
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
      if (result) {
        this.message = 'パスワード再設定用メールを送信しました。'
        this.http.post(this.hostname + "users/new", this.user).subscribe(
          /* postした時の操作があればここにかく */
        );
        let dialogRef = this.dialog.open(MessageModalComponent, {
          data: {
            message: this.message
          }
        });
      }
    });
  }

  ngOnInit() {
  }

  login() {
    this.authService.login(this.http, this.hostname + 'login', this.employeeNumber, this.password, () =>{
      console.log(this.authService.isLoggedIn);
      if(this.authService.isLoggedIn) {
        let redirect = this.authService.redirectUrl ? this.authService.redirectUrl : '/top';
        this.router.navigate([redirect]);
      } else {
        this.errorMessage = this.authService.message;
        console.log(this.errorMessage);
      }
    });
  }

  logout() {
    this.authService.logout();
  }
}
