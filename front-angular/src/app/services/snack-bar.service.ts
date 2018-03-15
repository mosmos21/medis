import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { Subject } from 'rxjs/Subject';
import { Router } from '@angular/router';

@Injectable()
export class SnackBarService {

  constructor(
    private snackBar: MatSnackBar,
    private router: Router,
  ) { }

  openSnackBar(message: string, link: string) {

    if (link == "") {
      let ref = this.snackBar.open(message, "閉じる", {
        duration: 1000,
        verticalPosition: 'top',
      });
      ref.onAction().subscribe(() => {
        ref.dismiss();
      });
    } else {
      let ref = this.snackBar.open(message, "ページへ移動", {
        duration: 3000,
        verticalPosition: 'top',
      });
      ref.onAction().subscribe(() => {
        this.router.navigate([link]);
      });
    }

  }
}

