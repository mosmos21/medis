import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-initialization',
  templateUrl: './initialization.component.html',
  styleUrls: ['./initialization.component.css']
})
export class InitializationComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<InitializationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) { }

  onClose(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
  }

}
