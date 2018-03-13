import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { CustomSnackbarComponent } from '../custom-snackbar/custom-snackbar.component';

@Injectable()
export class SnackBarService {

  constructor(public snackBar: MatSnackBar) { }

  openLoginSnackBar() {
    this.snackBar.openFromComponent(CustomSnackbarComponent, {
      duration: 5000,
      verticalPosition: 'top',
    });
  }

  openSaveSnackBar() {
    this.snackBar.openFromComponent(CustomSnackbarComponent, {
      duration: 5000,
      verticalPosition: 'top',
    });
  }

}
