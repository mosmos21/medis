import { Component, OnInit, Inject } from '@angular/core';
import { NavigationService } from '../services/navigation.service';
import { HttpClient } from '@angular/common/http';

import { AuthService } from '../services/auth.service';
import { ErrorService } from '../services/error.service';
import { MsgToSidenavService } from '../services/msg-to-sidenav.service';
import { HttpService } from '../services/http.service';

@Component({
  selector: 'app-sidenav-child',
  templateUrl: './sidenav-child.component.html',
  styleUrls: ['./sidenav-child.component.css']
})
export class SidenavChildComponent implements OnInit {

  public mymenuVisible: boolean;
  public searchVisible: boolean;
  public settingsVisible: boolean;
  private list: any;
  public num;

  constructor(
    public nav: NavigationService,
    private http: HttpService,
    @Inject('hostname') private hostname: string,
    private authService: AuthService,
    private errorService: ErrorService,
    private msgToSidenavService: MsgToSidenavService,
  ) {
    this.mymenuOpen();
  }

  ngOnInit() {
    if (this.nav.visible = true) {
      this.loadDraftList();
    }
    this.msgToSidenavService.msgToSidenavData$.subscribe(
      msg => {
        this.loadDraftList();
      }
    );
  }

  mymenuOpen() {
    this.mymenuVisible = true;
    this.searchVisible = false;
    this.settingsVisible = false;
  }

  searchOpen() {
    this.mymenuVisible = false;
    this.searchVisible = true;
    this.settingsVisible = false;
    this.nav.toSearchResult();
  }

  settingsOpen() {
    this.mymenuVisible = false;
    this.searchVisible = false;
    this.settingsVisible = true;
  }

  loadDraftList() {
    this.http.get("documents/private").subscribe(
      res => {
        this.list = res;
        this.num = this.list.length;
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
  }
}
