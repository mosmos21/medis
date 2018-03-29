import { Component, OnInit, Inject, HostListener, ViewChild } from '@angular/core';
import {
  MatDialog,
  MatTableDataSource,
  MatSort,
} from '@angular/material';
import { PapaParseService } from 'ngx-papaparse'

import { CreateUserComponent } from '../create-user/create-user.component'
import { CreateUsersByCsvComponent } from '../create-users-by-csv/create-users-by-csv.component'
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

  public displayedColumns = ["employeeNumber", "name", "mailaddress", "enabled", "reset"];
  public searchWord: string = '';
  private users: UserForm[] = new Array();

  public dataSource;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    public nav: NavigationService,
    public dialog: MatDialog,
    private papa: PapaParseService,
    private http: HttpService,
    private authService: AuthService,
    private errorService: ErrorService,
    private snackBarService: SnackBarService,
  ) {
    this.nav.showAdminMenu();
    this.nav.show();
    this.authService.getUserDetail();
    let csvData = '"Hello","World!"';

    this.papa.parse(csvData, {
      complete: (results, file) => {
        console.log('Parsed: ', results.data);
      }
    });
  }

  ngOnInit() {
    this.http.get('system/users').subscribe(users => {
      this.users = users;
      this.dataSource = new MatTableDataSource<UserForm>(users);
      this.dataSource.sort = this.sort;
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  confirmChangeEnable(user: UserForm): void {
    user.enabled = !user.enabled;
    this.http.post("system/users/update", user).subscribe(res => {
      this.snackBarService.openSnackBar('変更しました', '');
    }, error => {
      this.errorService.errorPath(error.status);
    });

  }

  initialization(user: UserForm): void {
    const data = {
      employeeNumber: user.employeeNumber,
      mailaddress: user.mailaddress,
      password: 'DUMMY',
    }

    const dialogRef = this.dialog.open(InitializationComponent, {
      data: {
        user: user,
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.http.post('accounts/usercheck', data).subscribe(success => {
        }, error => {
          this.errorService.errorPath(error.status);
        });
        this.snackBarService.openSnackBar(user.lastName + user.firstName + 'さんにパスワード初期化用メールを送信しました。', '');
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

  // changeEnable(index: number): void {
  //   user.enabled = !user.enabled;
  // }

  @HostListener('window:unload', ['$event'])
  unloadHandler() {
    this.http.post('users/update', this.users).subscribe(success => {
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  createUsersByCsv(): void {
    const dialogRef = this.dialog.open(CreateUsersByCsvComponent, {
      data: {}
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.papa.parse(result, {
          complete: (results) => {
            results.data.map(u => {
              return {
                employeeNumber: u[0],
                lastName: u[1],
                firstName: u[2],
                lastNamePhonetic: u[3],
                firstNamePhonetic: u[4],
                mailaddress: u[5],
                icon: false,
                authorityId: u[6],
                enabled: true,
              }
            }).forEach(element => {
              this.users.push(element);
              this.http.post('system/users/new', element).subscribe(success => {
              }, error => {
                this.errorService.errorPath(error.status);
              });
            });
          }
        });
      }
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
