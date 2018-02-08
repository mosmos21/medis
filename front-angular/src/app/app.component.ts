import { Component } from '@angular/core';
import { NavigationService } from './navigation.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  mymenuVisible: boolean;
  searchVisible: boolean;
  settingsVisible: boolean;

  constructor(private nav: NavigationService){ 
    this.nav.show();
    this.mymenuVisible=true;
  }
  ngOnInit(){ }

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
