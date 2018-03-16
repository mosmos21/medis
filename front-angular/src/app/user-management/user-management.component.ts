import { Component, OnInit, Inject, HostListener } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatDialog } from '@angular/material';

import { ConfirmationComponent } from '../confirmation/confirmation.component';
import { CreateUserComponent } from '../create-user/create-user.component'
import { MessageModalComponent } from '../message-modal/message-modal.component'
import { InitializationComponent } from '../initialization/initialization.component'
import { AuthService } from '../services/auth.service';
import { NavigationService } from '../services/navigation.service';
import { ErrorService } from '../services/error.service';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {

  private users: any = [{
    employeeNumber: '',
    lastName: '',
    firstName: '',
    lastNamePhonetic: '',
    firstNamePhonetic: '',
    mailaddress: '',
    icon: false,
    authorityId: '',
    enabled: true
  }];
  private enable;
  private user;
  public searchWord = "";

  private message;

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    public dialog: MatDialog,
    private authService: AuthService,
    private errorService: ErrorService,
    public nav: NavigationService
  ) {
    this.nav.showAdminMenu();
    this.nav.show();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.http.get(this.hostname + 'system/users', { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        this.users = json;
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
  }

  confirmChangeEnable(e: any, index: number): void {
    e.preventDefault();

    if (this.users[index]["enabled"]) {
      this.enable = "無効";
    } else {
      this.enable = "有効";
    }

    this.message = this.users[index]['lastName'] + this.users[index]['firstName'] + 'さんのアカウントを' + this.enable + '化します。'

    console.log(this.message)

    let dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        message: this.message
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.users[index]["enabled"] = !this.users[index]["enabled"];
      }
    });
  }

  initialization(index: number): void {

    this.message = this.users[index]['lastName'] + this.users[index]['firstName'] + 'さんにパスワード初期化用メールを送信しました。'

    this.user = {
      employeeNumber: this.users[index]["employeeNumber"],
      mailaddress: this.users[index]["mailaddress"]
    }

    let dialogRef = this.dialog.open(InitializationComponent, {
      data: {
        user: this.users[index],
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result != null) {
        this.user['password'] = 'dummypass'
        this.http.post(this.hostname + "accounts/usercheck", this.user, { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
          json => { },
          error => {
            this.errorService.errorPath(error.status);
          }
        );
        let dialogRef = this.dialog.open(MessageModalComponent, {
          data: {
            message: this.message
          }
        });
      }
    });
  }

  createUser(): void {

    this.user = {
      employeeNumber: '',
      lastName: '',
      firstName: '',
      lastNamePhonetic: '',
      firstNamePhonetic: '',
      mailaddress: '',
      icon: false,
      authorityId: '',
      enabled: true
    }

    let dialogRef = this.dialog.open(CreateUserComponent, {
      data: {
        user: this.user
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result != null) {
        this.users.push(result);
        this.http.post(this.hostname + "system/users/new", this.user, { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
          json => {
            console.log("success:");
            console.log(json);
          },
          error => {
            console.log("error:");
            console.log(error);
            this.errorService.errorPath(error.status)
          }
        );
      }
    });
  }

  changeEnable(index: number) {
    this.users[index]["enabled"] = !this.users[index]["enabled"];
  }

  searchUser() {
    var sub = JSON.stringify(this.users);
    var targetUser = JSON.parse(sub);
    var i = targetUser.length;
    var word = this.searchWord;
    while (i--) {
      if (
        (String(this.users[i]["employeeNumber"]).indexOf(word) == -1) &&
        (this.users[i]["lastName"].indexOf(word) == -1) &&
        (this.users[i]["firstName"].indexOf(word) == -1) &&
        (this.users[i]["lastNamePhonetic"].indexOf(word) == -1) &&
        (this.users[i]["firstNamePhonetic"].indexOf(word) == -1) &&
        (this.users[i]["mailaddress"].indexOf(word) == -1)
      ) {
        targetUser.splice(i, 1);
      }
    }
    return targetUser;
  }

  @HostListener('window:unload', ['$event'])
  unloadHandler() {
    this.http.post(this.hostname + "users/update", this.users, { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      /* postした時の操作があればここにかく */
    );
  }
}
