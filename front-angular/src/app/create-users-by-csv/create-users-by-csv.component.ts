import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-create-users-by-csv',
  templateUrl: './create-users-by-csv.component.html',
  styleUrls: ['./create-users-by-csv.component.css']
})
export class CreateUsersByCsvComponent implements OnInit {

  public file: File;
  public fileName: string;
  public message: string;

  constructor(public dialogRef: MatDialogRef<CreateUsersByCsvComponent>) { }

  ngOnInit() {
  }

  selectFile(event): void {
    this.file = event.target.files.item(0);
    console.log(this.file);
    if (this.file.name.match('.+.csv')) {
      this.message = null;
      this.fileName = this.file.name;
    } else {
      this.fileName = null;
      this.message = 'CSVファイルを指定してください。';
    }
  }
}
