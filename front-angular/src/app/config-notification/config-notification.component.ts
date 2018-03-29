import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { MatTableDataSource, MatSort } from '@angular/material';

import { HttpService } from '../services/http.service';
import { AuthService } from '../services/auth.service';
import { ErrorService } from '../services/error.service';
import { SnackBarService } from '../services/snack-bar.service';
import { NavigationService } from '../services/navigation.service';
import { TableService } from '../services/table.service';

import { Notification } from '../model/Notification';

@Component({
  selector: 'app-config-notification',
  templateUrl: './config-notification.component.html',
  styleUrls: ['./config-notification.component.css'],
})
export class ConfigNotificationComponent implements OnInit {

  public notification: Notification = new Notification();
  public isTagMail: boolean = true;
  public isTagBrowser: boolean = true;
  public existTags: boolean;

  public dataSource;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private nav: NavigationService,
    private http: HttpService,
    private authService: AuthService,
    private errorService: ErrorService,
    private snackBarService: SnackBarService,
    public tableService: TableService,
  ) {
    this.nav.show();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.http.getWithPromise('settings/me/tag_notifications').then(res => {
      this.dataSource = this.tableService.insertDataSourceTag(res);
      this.dataSource.sort = this.sort;
      console.log(this.dataSource);
      this.existTags = res.length != 0;
    }, error => {
      this.errorService.errorPath(error.status);
    });
    this.http.getWithPromise('settings/me/comment_notifications').then(res => {
      this.notification.setCommentNotification(res);
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  toggleTagMailAll(): void {
    for (let i = 0; i < this.dataSource._data.value.length; i++) {
      this.dataSource._data.value[i].mailNotification = this.isTagMail;
    }
  }

  toggleTagBrowserAll(): void {
    for (let i = 0; i < this.dataSource._data.value.length; i++) {
      this.dataSource._data.value[i].browserNotification = this.isTagBrowser;
    }
  }

  submit(): void {
    let data = this.dataSource._data.value;
    let success: boolean = true;
    const commentNotification = {
      mailNotification: this.notification.mailNotification,
      browserNotification: this.notification.browserNotification,
    };
    this.http.postWithPromise('settings/me/tag_notifications', data).then(res => {
      return this.http.postWithPromise('settings/me/comment_notifications', commentNotification);
    }, error => {
      success = false;
      this.errorService.errorPath(error.status);
    }).then(res => {
    }, error => {
      success = false;
      this.errorService.errorPath(error.status);
    }).then(() => {
      if (success) {
        this.snackBarService.openSnackBar('保存しました', '');
      }
    });
  }

  cancel(): void {
    this.nav.toTop();
  }
}
