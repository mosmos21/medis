import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';

import { HttpService } from '../services/http.service'
import { AuthService } from '../services/auth.service'


@Component({
  selector: 'app-edit-icon',
  templateUrl: './edit-icon.component.html',
  styleUrls: ['./edit-icon.component.css']
})
export class EditIconComponent implements OnInit {

  file: File;
  fileName: string;
  message: string;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 }

  constructor(
    public dialogRef: MatDialogRef<EditIconComponent>,
    private http: HttpService,
  ) { }

  ngOnInit() {
  }

  selectFile(event): void {
    this.file = event.target.files.item(0)
    console.log(this.file);
    if (this.file.type.match('image/png')) {
      this.message = null;
      this.fileName = this.file.name;
    } else {
      this.fileName = null;
      this.message = 'PNGファイルを指定してください。';
    }
  }

  upload(): void {
    this.currentFileUpload = this.file;
    this.http.postIcon(this.currentFileUpload);
    this.file = undefined;
    this.dialogRef.close(this.currentFileUpload);
  }

}
