import { Component, OnInit, Inject } from '@angular/core';

import { HttpService } from '../services/http.service';
import { AuthService } from '../services/auth.service';
import { ErrorService } from '../services/error.service';
import { SnackBarService } from '../services/snack-bar.service';
import { NavigationService } from '../services/navigation.service';

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

  constructor(
    private http: HttpService,
    private authService: AuthService,
    private errorService: ErrorService,
    private snackBarService: SnackBarService,
    private nav: NavigationService,
  ) {
    this.nav.show();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.http.getWithPromise('settings/me/tag_notifications').then(res => {
      this.notification.setTagNotification(res);
    }, error => {
      this.errorService.errorPath(error.status);
    });
    this.http.getWithPromise('settings/me/comment_notifications').then(res => {
      this.notification.setCommentNotification(res);
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  toggleTagMailAll() {
    for (const i in this.notification.tagNotification) {
      this.notification.tagNotification[i]['mailNotification'] = this.isTagMail;
    }
  }

  toggleTagBrowserAll() {
    for (let i in this.notification.tagNotification) {
      this.notification.tagNotification[i]['browserNotification'] = this.isTagBrowser;
    }
  }

  submit() {
    const commentNotification = {
      mailNotification: this.notification.mailNotification,
      browserNotification: this.notification.browserNotification,
    };
    console.log(commentNotification);
    console.log(this.notification.tagNotification);
    this.http.postWithPromise('settings/me/tag_notifications', this.notification.tagNotification);
    this.http.postWithPromise('settings/me/comment_notifications', commentNotification);
    this.snackBarService.openSnackBar('保存しました', '');
  }

  cancel() {
    this.nav.toTop();
  }
}
