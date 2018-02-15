import { Component, OnInit, Inject, HostListener } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatDialog } from '@angular/material';

import { ConfirmationComponent } from '../confirmation/confirmation.component';
import { CreateUserComponent } from '../create-user/create-user.component';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {

  private users;
  private enable;

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    public dialog: MatDialog,
  ) {
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
      width: '500px',
      height: '200px',
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

  createUser(): void {
    let dialogRef = this.dialog.open(CreateUserComponent, {
      width: '700px',
      height: '900px'
    });
  }

  changeEnable(index: number) {
    this.users[index]["isEnabled"] = !this.users[index]["isEnabled"];
  }

  @HostListener('window:unload', ['$event'])
  unloadHandler() {
    this.http.post(this.hostname + "users/update", this.users).subscribe(
      /* postした時の操作があればここにかく */
    );
  }
}
