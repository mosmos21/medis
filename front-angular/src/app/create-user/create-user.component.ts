import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ValidatorService } from '../services/validator.service'

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  errorMessage: string = '';

  public autorities = [
    { authorityId: 'a0000000000', authorityType: 'アドミニストレータ' },
    { authorityId: 'a0000000001', authorityType: '一般ユーザ' }
  ];

  constructor(
    public dialogRef: MatDialogRef<CreateUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private valid: ValidatorService,
  ) { }

  onClose(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    let newUser = [
      this.data.user.employeeNumber,
      this.data.user.lastName,
      this.data.user.firstName,
      this.data.user.lastNamePhonetic,
      this.data.user.firstNamePhonetic,
      this.data.user.mailaddress,
      this.data.user.authorityId,
    ]
    console.log(newUser);
    if (!this.valid.empty(newUser)) {
      this.dialogRef.close(this.data.user)
    } else {
      this.errorMessage = '入力必須項目が未入力です。'
    }
  }

  ngOnInit() {
  }

}
