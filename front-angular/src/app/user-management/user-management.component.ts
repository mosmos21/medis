import { Component, OnInit, Inject, HostListener } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatDialog } from '@angular/material';

import { ConfirmationComponent } from '../confirmation/confirmation.component';
import { CreateUserComponent } from '../create-user/create-user.component'
import { InitializationComponent } from '../initialization/initialization.component'
import { NavigationService } from '../services/navigation.service';

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
    isIcon: false,
    authorityId: '',
    isEnabled: true
  }];
  private enable;
  private user;
  private searchWord = "";

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    public dialog: MatDialog,
    public nav: NavigationService
  ) {
    this.nav.showAdminMenu();
  }

  ngOnInit() {
    this.http.get(this.hostname + 'users').subscribe(
      json => {
        this.users = json;
      },
      error => {
        this.users = error;
      }
    );
  }

  confirmChangeEnable(e: any, index: number): void {
    e.preventDefault();

    if (this.users[index]["isEnabled"]) {
      this.enable = "無効";
    } else {
      this.enable = "有効";
    }

    let dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        user: this.users[index],
        enable: this.enable,
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.users[index]["isEnabled"] = !this.users[index]["isEnabled"];
      }
    });
  }

  initialization(index: number): void {

    this.user = {
      employeeNumber: this.users[index]["employeeNumber"],
      mailaddress: this.users[index]["mailadress"]
    }

    let dialogRef = this.dialog.open(InitializationComponent, {
      data: {
        user: this.users[index],
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result != null) {
        this.http.post(this.hostname + "accounts/usercheck", this.user).subscribe(
          /* postした時の操作があればここにかく */
        );
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
      isIcon: false,
      authorityId: '',
      isEnabled: true
    }

    let dialogRef = this.dialog.open(CreateUserComponent, {
      data: {
        user: this.user
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.users.push(result);
        this.http.post(this.hostname + "users/new", this.user).subscribe(
          /* postした時の操作があればここにかく */
        );
      }
    });
  }

  changeEnable(index: number) {
    this.users[index]["isEnabled"] = !this.users[index]["isEnabled"];
  }

  searchUser() {
    var sub = JSON.stringify(this.users);
    var targetUser = JSON.parse(sub);
    var i = targetUser.length;
    var word = this.searchWord;
    console.log(this.users);
    while(i--) {
      if(
        (String(this.users[i]["employeeNumber"]).indexOf(word) == -1) &&
        (this.users[i]["lastName"].indexOf(word) == -1) &&
        (this.users[i]["firstName"].indexOf(word) == -1) &&
        (this.users[i]["lastNamePhonetic"].indexOf(word) == -1) &&
        (this.users[i]["firstNamePhonetic"].indexOf(word) == -1) &&
        (this.users[i]["mailaddress"].indexOf(word) == -1)
      ){
        targetUser.splice(i, 1);
      }
    }
    return targetUser;
  }

  @HostListener('window:unload', ['$event'])
  unloadHandler() {
    this.http.post(this.hostname + "users/update", this.users).subscribe(
      /* postした時の操作があればここにかく */
    );
  }
}
