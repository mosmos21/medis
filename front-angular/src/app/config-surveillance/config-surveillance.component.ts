import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { AuthService } from '../services/auth.service';
import { NavigationService } from '../services/navigation.service';
import { SearchService } from '../services/search.service';

@Component({
  selector: 'app-config-surveillance',
  templateUrl: './config-surveillance.component.html',
  styleUrls: ['./config-surveillance.component.css']
})
export class ConfigSurveillanceComponent implements OnInit {

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private nav: NavigationService,
    public searchService: SearchService,
    private authService: AuthService,
  ) {
    this.nav.show();
  }

  ngOnInit() {
    this.searchService.getTags();
  }

  save() {
    //保存ボタンが押された時の処理を書く
  }
}
