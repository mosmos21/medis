import { Injectable } from '@angular/core';

@Injectable()
export class NavigationService {
  private visible: boolean;
  private isAdmin: boolean = false;
  private isUser: boolean = true;

  constructor() {
    this.visible = false;
  }

  show() {
    this.visible = true;
  }

  hide() {
    this.visible = false;
  }

  adminMenu() {
    this.isAdmin = true;
    this.isUser = false;
  }

  userMenu() {
    this.isAdmin = false;
    this.isUser = true;
  }
}
