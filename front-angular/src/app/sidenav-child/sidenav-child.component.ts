import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidenav-child',
  templateUrl: './sidenav-child.component.html',
  styleUrls: ['./sidenav-child.component.css']
})
export class SidenavChildComponent implements OnInit {

  mymenuVisible: boolean;
  searchVisible: boolean;
  settingsVisible: boolean;

  constructor() {
    this.mymenuOpen();
  }

  ngOnInit() {
  }

  mymenuOpen(){
    this.mymenuVisible = true;
    this.searchVisible = false;
    this.settingsVisible = false;
  }

  searchOpen(){
    this.mymenuVisible = false;
    this.searchVisible = true;
    this.settingsVisible = false;
  }

  settingsOpen(){
    this.mymenuVisible = false;
    this.searchVisible = false;
    this.settingsVisible = true;
  }
}
