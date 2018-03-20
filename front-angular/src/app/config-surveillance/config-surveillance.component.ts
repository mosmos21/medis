import { Component, OnInit, Inject } from '@angular/core';

import { AuthService } from '../services/auth.service';
import { SearchService } from '../services/search.service';
import { SnackBarService } from '../services/snack-bar.service';
import { NavigationService } from '../services/navigation.service';
import { HttpService } from '../services/http.service';

@Component({
  selector: 'app-config-surveillance',
  templateUrl: './config-surveillance.component.html',
  styleUrls: ['./config-surveillance.component.css']
})
export class ConfigSurveillanceComponent implements OnInit {

  constructor(
    private http: HttpService,
    private nav: NavigationService,
    private snackBar: SnackBarService,
    private authService: AuthService,
    public searchService: SearchService,
  ) {
    this.nav.show();
    this.searchService.init();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.searchService.getTags();
    this.searchService.getMonitoringTag();
  }

  sabmit() {
    const tempTag = this.searchService.selectedTags;
    this.http.postWithPromise("settings/me/monitoring_tags", tempTag).subscribe(res => {
      this.snackBar.openSnackBar("保存しました", "");
    });
  }

  cancel() {
    this.nav.toTop();
  }
}