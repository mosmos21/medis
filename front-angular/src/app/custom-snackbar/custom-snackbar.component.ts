import { Component, OnInit } from '@angular/core';
import { MatSnackBarRef } from '@angular/material';

@Component({
  selector: 'app-custom-snackbar',
  templateUrl: './custom-snackbar.component.html',
  styleUrls: ['./custom-snackbar.component.css']
})
export class CustomSnackbarComponent implements OnInit {

  public message: string = "hogehoge";
  public link = "/new";

  constructor(
    private snackBarRef: MatSnackBarRef<CustomSnackbarComponent>
  ) { }

  ngOnInit() {
  }

  close() {
    this.snackBarRef.dismiss();
  }
}
