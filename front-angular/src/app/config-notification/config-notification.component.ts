import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { AuthService } from '../services/auth.service'
import { NavigationService } from '../services/navigation.service';
import { ErrorService } from '../services/error.service';
import { SnackBarService } from '../services/snack-bar.service';

@Component({
  selector: 'app-config-notification',
  templateUrl: './config-notification.component.html',
  styleUrls: ['./config-notification.component.css']
})
export class ConfigNotificationComponent implements OnInit {

  public mailNotification: boolean;
  public browserNotification: boolean;
  public isTagMail: boolean = true;
  public isTagBrowser: boolean = true;

  public tagNotification: any = [];
  private tempJson: any = [];

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private authService: AuthService,
    private errorService: ErrorService,
    private nav: NavigationService,
    private snackBarService: SnackBarService,
  ) {
    this.nav.show();
    this.authService.getUserDetail(http);
  }

  ngOnInit() {
    this.http.get(this.hostname + 'settings/me/tag_notifications', { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        this.tagNotification = json;
        console.log(this.tagNotification);
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );

    this.http.get(this.hostname + 'settings/me/comment_notifications', { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        this.tempJson = json;
        this.mailNotification = this.tempJson.mailNotification;
        this.browserNotification = this.tempJson.browserNotification;
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
  }

  toggleCommentMail() {
    this.mailNotification = !this.mailNotification;
  }

  toggleCommentBrowser() {
    this.browserNotification = !this.browserNotification;
  }

  toggleTagMailAll() {
    this.isTagMail = !this.isTagMail;
    for (const i in this.tagNotification) {
      this.tagNotification[i]["isMailNotification"] = this.isTagMail;
    }
  }

  toggleTagBrowserAll() {
    this.isTagBrowser = !this.isTagBrowser;
    for (let i in this.tagNotification) {
      this.tagNotification[i]["isBrowserNotification"] = this.isTagBrowser;
    }
  }

  toggleTagMail(idx: number) {
    this.tagNotification[idx].mailNotification = !this.tagNotification[idx].mailNotification;
  }

  toggleTagBrowser(idx: number) {
    this.tagNotification[idx].browserNotification = !this.tagNotification[idx].browserNotification;
  }

  submit() {
    // console.log(this.tagNotification);
    const commentNotification = {
      mailNotification: this.mailNotification,
      browserNotification: this.browserNotification,
    };
    // console.log(commentNotification);
    this.http.post(this.hostname + "settings/me/tag_notifications", this.tagNotification, { withCredentials: true, headers: this.authService.headerAddToken(), responseType: 'text' }).subscribe();
    this.http.post(this.hostname + "settings/me/comment_notifications", commentNotification, { withCredentials: true, headers: this.authService.headerAddToken(), responseType: 'text' }).subscribe();
    this.snackBarService.openSnackBar("保存しました", "");
  }

  cancel() {
    this.nav.toTop();
  }
}
