import { Injectable, Inject } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { Subject } from 'rxjs/Subject';
import { Router } from '@angular/router';

import { ErrorService } from '../services/error.service';
import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';
import { TypeConversionService } from '../services/type-conversion.service';

import { UpdateList } from '../model/UpdateList';
import { Notification } from '../model/Notification';


@Injectable()
export class SnackBarService {

  private updateList: any = [];
  private latestUpdateId: string;
  private notification: Notification = new Notification();
  public existTags: boolean;

  constructor(
    private snackBar: MatSnackBar,
    private router: Router,
    private errorService: ErrorService,
    private authService: AuthService,
    private http: HttpService,
    private typeConversionService: TypeConversionService,
  ) {
    this.updateMonitoring();
  }

  updateMonitoring(): void {
    setInterval(() => {
      if (this.authService.isLoggedIn()) {
        this.getUpdateList();
        if (this.updateList.length == 1) {
          this.openSnackBar(this.snackbarMsg(this.updateList[0].updateType), "/browsing/" + this.updateList[0].documentId);
        } else if (this.updateList.length > 1) {
          this.openSnackBar("更新情報が複数あります", "/top");
        }
      }
    }, 6000);
  }

  getUpdateList(): void {
    if (this.latestUpdateId) {
      this.http.get("update/" + this.latestUpdateId).subscribe(
        res => {
          console.log(res);
          this.updateList = this.typeConversionService.makeUpdateList(res);
          if (this.updateList.length > 0) {
            this.latestUpdateId = this.updateList[this.updateList.length - 1].updateId;
          }
        },
        error => {
          this.errorService.errorPath(error.status)
        }
      );
    } else {
      this.getUpdateId();
    }
  }

  openSnackBar(message: string, link: string): void {
    console.log("openSnackBar");
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

  snackbarMsg(type: string): string {
    if (type == "v0000000000") {
      return "監視しているタグに新規文書が投稿されました";
    } else if (type == "v0000000001") {
      return "監視しているタグの文書が更新されました"
    } else if (type == "v0000000002") {
      return "あなたの文書にコメントがつきました"
    } else if (type == "v0000000003") {
      return "あなたのコメントに既読がつきました";
    }
  }

  getUpdateId(): void {
    this.http.get("update/latest").subscribe(
      res => {
        this.latestUpdateId = res.updateId;
      }
    );
  }
}

