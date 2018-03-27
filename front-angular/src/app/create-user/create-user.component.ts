import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ValidatorService } from '../services/validator.service'

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  public errorMessage: string = '';

  public autorities = [
    { authorityId: 'a0000000000', authorityType: '管理者ユーザ' },
    { authorityId: 'a0000000001', authorityType: '一般ユーザ' }
  ];

  constructor(
    public dialogRef: MatDialogRef<CreateUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private validate: ValidatorService,
  ) { }

  onClose(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    let newUser: string[] = [
      this.data.user.employeeNumber,
      this.data.user.lastName,
      this.data.user.firstName,
      this.data.user.lastNamePhonetic,
      this.data.user.firstNamePhonetic,
      this.data.user.mailaddress,
      this.data.user.authorityId,
    ]
    this.errorMessage = "";
    console.log(newUser);

    if (this.validate.empty(newUser)) {
      this.errorMessage += "入力必須項目が未入力です";
    } else if (this.validate.employeeNumber(this.data.user.employeeNumber)) {
      this.errorMessage += "社員番号の形式が不正です";
    } else if (this.validate.kana(this.data.user.lastNamePhonetic, this.data.user.firstNamePhonetic)) {
      this.errorMessage += "フリガナは全角カナで入力してください";
    } else if (this.validate.mail(this.data.user.mailaddress)) {
      this.errorMessage += "メールアドレスの形式が不正です";
    } else {
      this.dialogRef.close(newUser)
    }
  }

  ngOnInit() {
  }

}
