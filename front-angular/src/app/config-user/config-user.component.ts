import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialog } from '@angular/material';
import { InitializationComponent } from '../initialization/initialization.component';
import { MessageModalComponent } from '../message-modal/message-modal.component';

import { AuthService } from '../services/auth.service';
import { NavigationService } from '../services/navigation.service';
import { ErrorService } from '../services/error.service';

@Component({
  selector: 'app-config-user',
  templateUrl: './config-user.component.html',
  styleUrls: ['./config-user.component.css']
})
export class ConfigUserComponent implements OnInit {

  public settings: any = [
    {
      employeeNumber: "",
      lastName: "",
      firstName: "",
      lastNamePhonetic: "",
      firstNamePhonetic: "",
      mailaddress: "",
      isIcon: ""
    }
  ];
  public tempSettings: any = [
    {
      employeeNumber: "",
      lastName: "",
      firstName: "",
      lastNamePhonetic: "",
      firstNamePhonetic: "",
      mailaddress: "",
      isIcon: ""
    }
  ];
  private message;
  public isIconInput: boolean;

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    public dialog: MatDialog,
    private authService: AuthService,
    private errorService: ErrorService,
    private nav: NavigationService
  ) {
    this.nav.show();
  }

  ngOnInit() {
    this.http.get(this.hostname + 'settings/me', { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        this.settings = json;
        var sub = JSON.stringify(this.settings);
        this.tempSettings = JSON.parse(sub);
        console.log(this.settings);
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
  }

  initialization() {
    this.message = this.settings["mailaddress"] + '宛にパスワード初期化用メールを送信しました。'

    var user = {
      employeeNumber: this.settings["employeeNumber"],
      mailaddress: this.settings["mailadress"]
    }

    let dialogRef = this.dialog.open(InitializationComponent, {
      data: {
        user: this.settings,
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result != null) {
        this.http.post(this.hostname + "accounts/usercheck", this.settings, { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
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

  editIsIcon() {
    this.isIconInput = true;
  }

  submit() {
    this.settings = this.tempSettings;
    this.http.post(this.hostname + "settings/me", this.settings, { withCredentials: true, headers: this.authService.headerAddToken(), responseType: 'text' }).subscribe();
    this.nav.toTop();
  }

  resetAll() {
    this.resetLastName();
    this.resetFirstName();
    this.resetLastNamePhonetic();
    this.resetFirstNamePhonetic();
    this.resetMailaddress();
    this.resetIsIcon();
  }

  resetLastName() {
    this.tempSettings.lastName = this.settings.lastName;
  }

  resetFirstName() {
    this.tempSettings.firstName = this.settings.firstName;
  }

  resetLastNamePhonetic() {
    this.tempSettings.lastNamePhonetic = this.settings.lastNamePhonetic;
  }

  resetFirstNamePhonetic() {
    this.tempSettings.firstNamePhonetic = this.settings.firstNamePhonetic;
  }

  resetMailaddress() {
    this.tempSettings.mailaddress = this.settings.mailaddress;
  }

  resetIsIcon() {
    this.isIconInput = false;
    this.tempSettings.isIcon = this.settings.isIcon;
  }
}
