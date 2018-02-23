import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-config-notification',
  templateUrl: './config-notification.component.html',
  styleUrls: ['./config-notification.component.css']
})
export class ConfigNotificationComponent implements OnInit {

  private isCommentMail: boolean;
  private isCommentBrowser: boolean;
  private isTagMail: boolean = true;
  private isTagBrowser: boolean = true;

  private tagNotification: any = [
    {
      tagId: "",
      tagName: "",
      isMailNotification: "",
      isBrowserNotification: ""
    }
  ];

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
  ) { }

  ngOnInit() {
    this.http.get(this.hostname + 'notifications').subscribe(
      json => {
        this.tagNotification = json;
        console.log(this.tagNotification);
      },
      error => {
        this.tagNotification = error;
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
