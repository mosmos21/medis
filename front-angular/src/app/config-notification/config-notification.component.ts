import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { AuthService } from '../services/auth.service'
import { NavigationService } from '../services/navigation.service';
import { ErrorService } from '../services/error.service';

@Component({
  selector: 'app-config-notification',
  templateUrl: './config-notification.component.html',
  styleUrls: ['./config-notification.component.css']
})
export class ConfigNotificationComponent implements OnInit {

  public isCommentMail: boolean;
  public isCommentBrowser: boolean;
  public isTagMail: boolean = true;
  public isTagBrowser: boolean = true;

  public tagNotification: any = [];
  private tempJson: any = [
    {
      isMailNotification: "",
      isBrowserNotification: "",
    }
  ];

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private authService: AuthService,
    private errorService: ErrorService,
    private nav: NavigationService
  ) {
    this.nav.show();
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
        this.isCommentMail = this.tempJson.isMailNotification;
        this.isCommentBrowser = this.tempJson.isBrowserNotification;
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
  }

  toggleCommentMail() {
    this.isCommentMail = !this.isCommentMail;
  }

  toggleCommentPush() {
    this.isCommentBrowser = !this.isCommentBrowser;
  }

  toggleTagMail() {
    this.isTagMail = !this.isTagMail;
    for (const i in this.tagNotification) {
      this.tagNotification[i]["isMailNotification"] = this.isTagMail;
    }
  }

  toggleTagPush() {
    this.isTagBrowser = !this.isTagBrowser;
    for (const i in this.tagNotification) {
      this.tagNotification[i]["isBrowserNotification"] = this.isTagBrowser;
    }
  }

  submit() {

  }

  reset() {

  }
}
