import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import {
  FileSelectDirective,
  FileDropDirective,
  FileUploader,
} from 'ng2-file-upload/ng2-file-upload';

@Component({
  selector: 'app-edit-icon',
  templateUrl: './edit-icon.component.html',
  styleUrls: ['./edit-icon.component.css']
})
export class EditIconComponent implements OnInit {

  public uploader:FileUploader = new FileUploader({url: ''});
  public hasBaseDropZoneOver:boolean = false;
  public hasAnotherDropZoneOver:boolean = false;

  constructor(
    public dialogRef: MatDialogRef<EditIconComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) { }

  onClose(): void {
    this.dialogRef.close();
  }

  public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
  }
 
  public fileOverAnother(e:any):void {
    this.hasAnotherDropZoneOver = e;
  }

  ngOnInit() {
  }

}
