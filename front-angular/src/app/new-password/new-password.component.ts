import { MatDialog } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit, Inject } from '@angular/core';

import { MessageModalComponent } from '../message-modal/message-modal.component';

import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';
import { ErrorService } from '../services/error.service';
import { NavigationService } from '../services/navigation.service';

@Component({
  selector: 'app-new-password',
  templateUrl: './new-password.component.html',
  styleUrls: ['./new-password.component.css']
})
export class NewPasswordComponent implements OnInit {

  private user = {
    employeeNumber: '',
    mailaddress: '',
    password: '',
  };
  public password = '';
  public passwordCheck = '';
  public errorMessage = '';
  public hide: boolean;
  public hide_check: boolean;

  constructor(
    public dialog: MatDialog,
    private nav: NavigationService,
    private http: HttpService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private errorService: ErrorService,
  ) {
    nav.hide();
  }

  ngOnInit() {
    this.route.queryParams.subscribe(qParams => {
      const params = {
        secret: qParams['secret'],
      }
      this.http.post('accounts/keycheck', params).subscribe(res => {
        const result = JSON.parse(res);
        if (result['result'] == 'NG') {
          const dialogRef = this.dialog.open(MessageModalComponent, {
            data: {
              message: res['message'],
            }
          });
          dialogRef.afterClosed().subscribe(result => {
            this.router.navigate(['/login']);
          });
        } else {
          this.user['employeeNumber'] = result['employeeNumber'];
          this.user['mailaddress'] = result['mailaddress'];
        }
      }, error => {
        this.errorService.errorPath(error.status);
      }
      );
    })
  }

  sendNewPassword() {
    if (this.password == this.passwordCheck) {
      this.errorMessage = '';
      this.user['password'] = this.password;
      this.http.post('accounts/reset', this.user).subscribe(success => {
        const dialogRef = this.dialog.open(MessageModalComponent, {
          data: {
            message: 'パスワードの初期化が完了しました'
          }
        });
        dialogRef.afterClosed().subscribe(result => {
          this.router.navigate(['/login']);
        });
      }, error => {
        this.errorService.errorPath(error.status);
      });
    } else {
      this.errorMessage = '新しいパスワードと新しいパスワード（確認）が異なります。';
    }
  }


}
