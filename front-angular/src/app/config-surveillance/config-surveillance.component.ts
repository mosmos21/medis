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
    this.authService.getUserDetail(http);
    this.searchService.init();
  }

  ngOnInit() {
    this.searchService.getTags();
    this.searchService.getMonitoringTag();
  }

  sabmit() {
    console.log(this.searchService.selectedTags);
    const tempTag = this.searchService.selectedTags;
    this.http.post(this.hostname + "settings/me/monitoring_tags", tempTag, { withCredentials: true, headers: this.authService.headerAddToken(), responseType: 'text' }).subscribe();
  }
}