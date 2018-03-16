import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { AuthService } from '../services/auth.service';
import { NavigationService } from '../services/navigation.service';
import { SearchService } from '../services/search.service';
import { SnackBarService } from '../services/snack-bar.service';

@Component({
  selector: 'app-config-surveillance',
  templateUrl: './config-surveillance.component.html',
  styleUrls: ['./config-surveillance.component.css']
})
export class ConfigSurveillanceComponent implements OnInit {

  constructor(
    private http: HttpClient,
    private nav: NavigationService,
    private snackBar: SnackBarService,
    @Inject('hostname') private hostname: string,
    private authService: AuthService,
    public searchService: SearchService,
  ) {
    this.nav.show();
    this.authService.getUserDetail();
    this.searchService.init();
  }

  ngOnInit() {
    this.searchService.getTags();
    this.searchService.getMonitoringTag();
  }

  sabmit() {
    console.log(this.searchService.selectedTags);
    const tempTag = this.searchService.selectedTags;
    this.http.post(this.hostname + "settings/me/monitoring_tags", tempTag, { withCredentials: true, headers: this.authService.headerAddToken(), responseType: 'text' }).subscribe(
      success => {
        this.snackBar.openSnackBar("保存しました", "");
      }
    );
  }
  cancel() {
    console.log("call cancel");
    this.nav.toTop();
  }
}