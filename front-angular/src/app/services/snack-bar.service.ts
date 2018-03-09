import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material'

@Injectable()
export class SnackBarService {

  constructor(public snackBar: MatSnackBar) { }

  openSnackBar(message: string) {
    this.snackBar.open(message, "閉じる", {
      duration: 2000,
    });
  }

}
