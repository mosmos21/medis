import { Component, OnInit, Inject, HostListener } from '@angular/core';
import { MatDialog } from '@angular/material';

import { CreateUserComponent } from '../create-user/create-user.component'
import { ConfirmationComponent } from '../confirmation/confirmation.component';
import { MessageModalComponent } from '../message-modal/message-modal.component'
import { InitializationComponent } from '../initialization/initialization.component'

import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';
import { ErrorService } from '../services/error.service';
import { SnackBarService } from '../services/snack-bar.service';
import { NavigationService } from '../services/navigation.service';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {

  public searchWord: string = '';
  private users: UserForm[] = new Array();

  constructor(
    public nav: NavigationService,
    public dialog: MatDialog,
    private http: HttpService,
    private authService: AuthService,
    private errorService: ErrorService,
    private snackBarService: SnackBarService,
  ) {
    this.nav.showAdminMenu();
    this.nav.show();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.http.get('system/users').subscribe(users => {
      this.users = users;
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  confirmChangeEnable(e: any, index: number): void {
    e.preventDefault();
    const enable = this.users[index].enabled ? '無効' : '有効';
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        message: this.users[index].lastName + this.users[index].firstName + 'さんのアカウントを' + enable + '化します。'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.users[index].enabled = !this.users[index].enabled;
        this.snackBarService.openSnackBar('変更しました', '');
      }
    });
  }

  initialization(index: number): void {
    const data = {
      employeeNumber: this.users[index].employeeNumber,
      mailaddress: this.users[index].mailaddress,
      password: 'DUMMY',
    }

    const dialogRef = this.dialog.open(InitializationComponent, {
      data: {
        user: this.users[index],
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.http.post('accounts/usercheck', data).subscribe(success => {
        }, error => {
          this.errorService.errorPath(error.status);
        });
        this.snackBarService.openSnackBar(this.users[index].lastName + this.users[index].firstName + 'さんにパスワード初期化用メールを送信しました。', '');
      }
    });
  }

  createUser(): void {
    let user: UserForm = new UserForm();
    user.enabled = true;
    const dialogRef = this.dialog.open(CreateUserComponent, {
      data: {
        user: user,
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.users.push(result);
        this.http.post('system/users/new', user).subscribe(success => {
        }, error => {
          this.errorService.errorPath(error.status);
        });
      }
    });
  }

  changeEnable(index: number): void {
    this.users[index].enabled = !this.users[index].enabled;
  }

  searchUser(): UserForm[] {
    return this.users.filter(user =>
      user.employeeNumber.includes(this.searchWord)
      || user.lastName.includes(this.searchWord)
      || user.firstName.includes(this.searchWord)
      || user.lastNamePhonetic.includes(this.searchWord)
      || user.firstNamePhonetic.includes(this.searchWord)
      || user.mailaddress.includes(this.searchWord)
    );
  }

  @HostListener('window:unload', ['$event'])
  unloadHandler() {
    this.http.post('users/update', this.users).subscribe(success => {
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }
}

class UserForm {
  public employeeNumber: string;
  public lastName: string;
  public firstName: string;
  public lastNamePhonetic: string;
  public firstNamePhonetic: string;
  public mailaddress: string;
  public icon: boolean;
  public authorityId: string;
  public enabled: boolean;
}
