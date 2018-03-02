import { Injectable } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { SearchService } from './search.service';

@Injectable()
export class NavigationService {
  public visible: boolean;
  public isUser: boolean;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private searchService: SearchService,
  ) {
    this.visible = false;
    this.isUser = true;
  }

  show() {
    this.visible = true;
  }

  hide() {
    this.visible = false;
  }

  showAdminMenu() {
    this.isUser = false;
  }

  showUserMenu() {
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
    this.searchService.selectedTags = [];
    this.router.navigate(["/settings/me"]);
  }

  toNotificationSetting() {
    this.searchService.selectedTags = [];
    this.router.navigate(["/settings/me/notification"]);
  }

  toMonitoringTagsSetting() {
    this.searchService.selectedTags = [];
    this.router.navigate(["settings/me/monitoring_tags"]);
  }

  toSearchResult() {
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
