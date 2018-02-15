import { Component } from '@angular/core';
import { NavigationService } from './services/navigation.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  private mymenuVisible: boolean;
  private searchVisible: boolean;
  private settingsVisible: boolean;

  constructor(private nav: NavigationService) {
    this.nav.show();
    // this.nav.userMenu();
    this.mymenuVisible = true;
  }
  ngOnInit() { }

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
}
