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
  private tempSettings: any = [
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
  private isIconInput: boolean;

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
        this.tempSettings = JSON.parse(sub);
        console.log(this.tempSettings[0]);
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

  editIsIcon() {
    this.isIconInput = true;
  }

  submit() {
    this.settings = this.tempSettings;
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
    this.tempSettings[0].lastName = this.settings[0].lastName;
  }

  resetFirstName() {
    this.tempSettings[0].firstName = this.settings[0].firstName;
  }

  resetLastNamePhonetic() {
    this.tempSettings[0].lastNamePhonetic = this.settings[0].lastNamePhonetic;
  }

  resetFirstNamePhonetic() {
    this.tempSettings[0].firstNamePhonetic = this.settings[0].firstNamePhonetic;
  }

  resetMailaddress() {
    this.tempSettings[0].mailaddress = this.settings[0].mailaddress;
  }

  resetIsIcon() {
    this.isIconInput = false;
    this.tempSettings[0].isIcon = this.settings[0].isIcon;
  }
}
