import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  public autorities = [
    { authorityId: 'a0000000000', authorityType: '一般ユーザ' },
    { authorityId: 'a0000000001', authorityType: 'アドミニストレータ' }
  ];

  constructor(
    public dialogRef: MatDialogRef<CreateUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) { }

  onClose(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
  }

}
