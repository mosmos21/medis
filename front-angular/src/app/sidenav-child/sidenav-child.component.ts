import { Component, OnInit, Inject } from '@angular/core';
import { NavigationService } from '../services/navigation.service';
import { HttpClient } from '@angular/common/http';

import { AuthService } from '../services/auth.service';
import { ErrorService } from '../services/error.service';

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
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private authService: AuthService,
    private errorService: ErrorService,
  ) {
    this.mymenuOpen();
  }

  ngOnInit() {
    if (this.nav.visible = true) {
      this.loadList();
    }
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

  logout() {

  }

  loadList() {
    this.http.get(this.hostname + "documents/private", { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        this.list = json;
        this.num = this.list.length;
        console.log(this.list);
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
  }
}
