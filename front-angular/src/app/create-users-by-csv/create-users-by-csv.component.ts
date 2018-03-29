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
    if (this.file == null) {
      this.fileName = null;
      this.message = 'ファイルが選択されていません。';
    } else if (!this.file.name.match('.+.csv')) {
      this.fileName = null;
      this.message = 'CSVファイルを指定してください。';
    } else {
      this.message = null;
      this.fileName = this.file.name;
    }
  }
}
