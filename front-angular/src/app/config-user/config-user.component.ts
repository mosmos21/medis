import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialog } from '@angular/material';
import { InitializationComponent } from '../initialization/initialization.component';
import { MessageModalComponent } from '../message-modal/message-modal.component';

@Component({
  selector: 'app-config-user',
  templateUrl: './config-user.component.html',
  styleUrls: ['./config-user.component.css']
})
export class ConfigUserComponent implements OnInit {

  private settings: any = [
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
  private temp_settings;
  private message;

  private lastNameInput: boolean = false;
  private firstNameInput: boolean = false;
  private lastNamePhoneticInput: boolean = false;
  private firstNamePhoneticInput: boolean = false;
  private mailaddressInput: boolean = false;
  private isIconInput: boolean = false;

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    public dialog: MatDialog,
  ) { }

  ngOnInit() {
    this.http.get(this.hostname + 'settings/me').subscribe(
      json => {
        this.settings = json;
        var sub = JSON.stringify(this.settings);
        this.temp_settings = JSON.parse(sub);
      },
      error => {
        this.settings = error;
      }
    );
  }

  initialization() {
    this.message = this.settings[0]["mailaddress"] + '宛にパスワード初期化用メールを送信しました。'

    var user = {
      employeeNumber: this.settings[0]["employeeNumber"],
      mailaddress: this.settings[0]["mailadress"]
    }

    let dialogRef = this.dialog.open(InitializationComponent, {
      data: {
        user: this.settings[0],
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result != null) {
        this.http.post(this.hostname + "accounts/usercheck", this.settings).subscribe(
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

  editLastName() {
    this.lastNameInput = true;
  }

  editFirstName() {
    this.firstNameInput = true;
  }

  editLastNamePhonetic() {
    this.lastNamePhoneticInput = true;
  }

  editFirstNamePhonetic() {
    this.firstNamePhoneticInput = true;
  }

  editMailAddress() {
    this.mailaddressInput = true;
  }

  editIsIcon() {
    this.isIconInput = true;
  }

  apply() {
    this.lastNameInput = false;
    this.firstNameInput = false;
    this.lastNamePhoneticInput = false;
    this.lastNamePhoneticInput = false;
    this.mailaddressInput = false;
    this.isIconInput = false;
  }

  submit() {
    console.log(this.settings);
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
    this.lastNameInput = false;
    this.temp_settings[0].lastName = this.settings[0].lastName;
  }

  resetFirstName() {
    this.firstNameInput = false;
    this.temp_settings[0].firstName = this.settings[0].firstName;
  }

  resetLastNamePhonetic() {
    this.lastNamePhoneticInput = false;
    this.temp_settings[0].lastNamePhonetic = this.settings[0].lastNamePhonetic;
  }

  resetFirstNamePhonetic() {
    this.firstNamePhoneticInput = false;
    this.temp_settings[0].firstNamePhonetic = this.settings[0].firstNamePhonetic;
  }

  resetMailaddress() {
    this.mailaddressInput = false;
    this.temp_settings[0].mailaddress = this.settings[0].mailaddress;
  }

  resetIsIcon() {
    this.isIconInput = false;
    this.temp_settings[0].isIcon = this.settings[0].isIcon;
  }
}
