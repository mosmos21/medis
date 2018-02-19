import { Component, OnInit } from '@angular/core';
import { NavigationService } from '../services/navigation.service';

@Component({
  selector: 'app-sidenav-child',
  templateUrl: './sidenav-child.component.html',
  styleUrls: ['./sidenav-child.component.css']
})
export class SidenavChildComponent implements OnInit {

  private mymenuVisible: boolean;
  private searchVisible: boolean;
  private settingsVisible: boolean;

  constructor(private nav: NavigationService) {
    this.mymenuOpen();
  }

  ngOnInit() {
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
  }

  settingsOpen() {
    this.mymenuVisible = false;
    this.searchVisible = false;
    this.settingsVisible = true;
  }

  logout() {

  }
}
