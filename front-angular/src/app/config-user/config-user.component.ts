import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog } from '@angular/material';

import { InitializationComponent } from '../initialization/initialization.component';
import { MessageModalComponent } from '../message-modal/message-modal.component';
import { EditIconComponent } from '../edit-icon/edit-icon.component';

import { HttpService } from '../services/http.service';
import { AuthService } from '../services/auth.service';
import { ErrorService } from '../services/error.service';
import { SnackBarService } from '../services/snack-bar.service';
import { ValidatorService } from '../services/validator.service';
import { NavigationService } from '../services/navigation.service';

import { UserSettings } from '../model/UserSettings';

@Component({
  selector: 'app-config-user',
  templateUrl: './config-user.component.html',
  styleUrls: ['./config-user.component.css'],
})
export class ConfigUserComponent implements OnInit {

  public errorMessage: string = '';

  public userSettings: UserSettings = new UserSettings();
  public tempUserSettings: UserSettings = new UserSettings();

  private message: string;

  constructor(
    public dialog: MatDialog,
    public authService: AuthService,
    private http: HttpService,
    private errorService: ErrorService,
    private snacBarService: SnackBarService,
    private validate: ValidatorService,
    private nav: NavigationService,
  ) {
    this.nav.show();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.http.getWithPromise('settings/me').then(res => {
      this.userSettings.setSettings(res);
      let sub = JSON.stringify(res);
      this.tempUserSettings.setSettings(JSON.parse(sub));
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  initialization(): void {
    this.message = this.userSettings.mailaddress + '宛にパスワード初期化用メールを送信しました。';
    let dialogRef = this.dialog.open(InitializationComponent, {
      data: {
        user: this.userSettings,
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      let resetUser: any = {
        employeeNumber: this.userSettings.employeeNumber,
        mailaddress: this.userSettings.mailaddress,
        password: 'dammy'
      }
      if (result != null) {
        this.http.postWithPromise('accounts/usercheck', resetUser).then(res => { }, error => {
          this.errorService.errorPath(error.status);
        });
        let dialogRef = this.dialog.open(MessageModalComponent, {
          data: {
            message: this.message,
          }
        });
      }
    });
  }

  editIcon(): void {
    let dialogRef = this.dialog.open(EditIconComponent);
    dialogRef.afterClosed().subscribe(result => {
      this.authService.userdetail.iconUrl = '';
      this.authService.getUserDetail();
    })
  }

  submit(): void {
    this.userSettings = this.tempUserSettings;
    let editUser = [
      this.userSettings.lastName,
      this.userSettings.firstName,
      this.userSettings.lastNamePhonetic,
      this.userSettings.firstNamePhonetic,
      this.userSettings.mailaddress,
    ]
    this.errorMessage = "";

    if (this.validate.empty(editUser)) {
      this.errorMessage += "入力必須項目が未入力です";
    } else if (this.validate.kana(this.userSettings.lastNamePhonetic, this.userSettings.firstNamePhonetic)) {
      this.errorMessage += "フリガナは全角カナで入力してください";
    } else if (this.validate.mail(this.userSettings.mailaddress)) {
      this.errorMessage += "メールアドレスの形式が不正です";
    } else {
      this.http.postWithPromise('settings/me', this.userSettings).then(res => { }, error => {
        this.errorService.errorPath(error.status);
      });
      this.nav.toTop();
      this.snacBarService.openSnackBar('保存しました', '');
    }
  }

  resetAll(): void {
    this.nav.toTop();
  }

  resetLastName(): void {
    this.tempUserSettings.lastName = this.userSettings.lastName;
  }

  resetFirstName(): void {
    this.tempUserSettings.firstName = this.userSettings.firstName;
  }

  resetLastNamePhonetic(): void {
    this.tempUserSettings.lastNamePhonetic = this.userSettings.lastNamePhonetic;
  }

  resetFirstNamePhonetic(): void {
    this.tempUserSettings.firstNamePhonetic = this.userSettings.firstNamePhonetic;
  }

  resetMailaddress(): void {
    this.tempUserSettings.mailaddress = this.userSettings.mailaddress;
  }
}
