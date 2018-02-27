import { Injectable } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Injectable()
export class NavigationService {
  private visible: boolean;
  private isAdmin: boolean;
  private isUser: boolean;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
  ) {
    this.visible = true;
    this.isUser = true;
  }

  show() {
    this.visible = true;
  }

  hide() {
    this.visible = false;
  }

  showAdminMenu() {
    this.isAdmin = true;
    this.isUser = false;
  }

  showUserMenu() {
    this.isAdmin = false;
    this.isUser = true;
  }

  //一般ユーザ画面遷移
  toTop() {
    this.router.navigate(["/top"]);
  }

  toEdit(str) {
    if (!str) {
      this.router.navigate(["/edit"]);
    } else if (str == "new") {
      this.router.navigate(["/edit"], { fragment: "new" });
    } else if (typeof str == "string") {
      this.router.navigate(["/edit", str]);
    }
  }

  //一般ユーザ設定系
  toMySetting() {
    this.router.navigate(["/settings/me"]);
  }

  toNotificationSetting() {
    this.router.navigate(["/settings/me/notification"]);
  }

  toMonitoringTagsSetting() {
    this.router.navigate(["settings/me/monitoring_tags"]);
  }

  toSearchResult() {
    console.log("call toSearchResult");
    this.router.navigate(["search"]);
  }

  //Adminユーザ画面遷移
  toTemplate() {
    this.router.navigate(["/admin/template"]);
  }

  toUserManagement() {
    this.router.navigate(["/admin/management"]);
  }

}
