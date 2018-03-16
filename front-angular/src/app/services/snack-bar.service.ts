import { Injectable, Inject } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { Subject } from 'rxjs/Subject';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { ErrorService } from '../services/error.service';
import { AuthService } from '../services/auth.service';
import { resolve } from 'dns';

@Injectable()
export class SnackBarService {

  private updateList: any = [];

  constructor(
    private snackBar: MatSnackBar,
    private router: Router,
    private http: HttpClient,
    private errorService: ErrorService,
    @Inject('hostname') private hostname: string,
    private authService: AuthService,
  ) {
    this.updateMonitoring();
  }

  async updateMonitoring() {
    setInterval(() => {
      if (this.authService.isLoggedIn()) {
        this.getUpdateList();
        if (this.updateList.length == 1) {
          this.openSnackBar(this.snackbarMsg(), "/browsing/" + this.updateList[0].documentId);
        } else if (this.updateList.length > 1) {
          this.openSnackBar("更新情報が複数あります", "/top");
        }
      }
    }, 5000);
  }

  getUpdateList() {
    this.http.get(this.hostname + "documents", { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        this.updateList = json;
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
  }

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

  snackbarMsg(): string {
    if (this.updateList[0].updateType == "v0000000000") {
      return "監視しているタグに新規文書が投稿されました";
    } else if (this.updateList[0].updateType == "v0000000001") {
      return "監視しているタグの文書が更新されました"
    } else if (this.updateList[0].updateType == "v0000000002") {
      return "あなたの文書にコメントがつきました"
    } else {
      return "あなたのコメントに既読がつきました";
    }
  }
}

