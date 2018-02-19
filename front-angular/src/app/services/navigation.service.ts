import { Injectable } from '@angular/core';

@Injectable()
export class NavigationService {
  private visible: boolean;
  private isAdmin: boolean;
  private isUser: boolean;

  constructor() {
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
    this.isAdmin = true;
    this.isUser = false;
  }

  showUserMenu() {
    this.isAdmin = false;
    this.isUser = true;
  }
}
