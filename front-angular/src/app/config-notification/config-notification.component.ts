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
  public existTags: boolean;

  constructor(
    private nav: NavigationService,
    private http: HttpService,
    private authService: AuthService,
    private errorService: ErrorService,
    private snackBarService: SnackBarService,
  ) {
    this.nav.show();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.http.getWithPromise('settings/me/tag_notifications').then(res => {
      this.notification.setTagNotification(res);
      this.existTags = this.notification.tagNotification.length != 0;
      console.log(this.existTags);
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
    for (let i in this.notification.tagNotification) {
      this.notification.tagNotification[i]['mailNotification'] = this.isTagMail;
    }
  }

  toggleTagBrowserAll(): void {
    for (let i in this.notification.tagNotification) {
      this.notification.tagNotification[i]['browserNotification'] = this.isTagBrowser;
    }
  }

  submit(): void {
    let success: boolean = true;
    const commentNotification = {
      mailNotification: this.notification.mailNotification,
      browserNotification: this.notification.browserNotification,
    };
    this.http.postWithPromise('settings/me/tag_notifications', this.notification.tagNotification).then(res => {
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
