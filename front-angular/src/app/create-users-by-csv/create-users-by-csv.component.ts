import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-create-users-by-csv',
  templateUrl: './create-users-by-csv.component.html',
  styleUrls: ['./create-users-by-csv.component.css']
})
export class CreateUsersByCsvComponent implements OnInit {

  public file: File;

  constructor(public dialogRef: MatDialogRef<CreateUsersByCsvComponent>) { }

  ngOnInit() {
  }

  selectFile(event): void {
    this.file = event.target.files.item(0)
    console.log(this.file);
  }
}
