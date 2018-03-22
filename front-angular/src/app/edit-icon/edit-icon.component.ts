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

  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 }

  constructor(
    public dialogRef: MatDialogRef<EditIconComponent>,
    private http: HttpService,
  ) { }

  ngOnInit() {
  }

  selectFile(event): void {
    const file = event.target.files.item(0)

    if (file.type.match('image.*')) {
      this.selectedFiles = event.target.files;
    } else {
      alert('invalid format!');
    }
  }

  upload(): void {
    this.currentFileUpload = this.selectedFiles.item(0);
    this.http.postIcon(this.currentFileUpload);
    this.selectedFiles = undefined;
    this.dialogRef.close(this.currentFileUpload);
  }

}
