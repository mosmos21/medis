import { Component, OnInit, Inject } from '@angular/core';
import { NavigationService } from '../services/navigation.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-sidenav-child',
  templateUrl: './sidenav-child.component.html',
  styleUrls: ['./sidenav-child.component.css']
})
export class SidenavChildComponent implements OnInit {

  private mymenuVisible: boolean;
  private searchVisible: boolean;
  private settingsVisible: boolean;
  private list: any;
  private num;

  constructor(
    private nav: NavigationService,
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
  ) {
    this.mymenuOpen();
  }

  ngOnInit() {
    this.loadList();
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

  loadList(): void {
    this.http.get(this.hostname + "documents").subscribe(
      json => {
        this.list = json;
        this.num = this.list.length;
      },
      error => {
        // TODO;
      }
    );
  }
}
