import { Component, OnInit, Inject } from '@angular/core';

import { HttpService } from '../services/http.service';
import { AuthService } from '../services/auth.service';
import { ErrorService } from '../services/error.service'
import { SearchService } from '../services/search.service';
import { SnackBarService } from '../services/snack-bar.service';
import { NavigationService } from '../services/navigation.service';

@Component({
  selector: 'app-config-surveillance',
  templateUrl: './config-surveillance.component.html',
  styleUrls: ['./config-surveillance.component.css']
})
export class ConfigSurveillanceComponent implements OnInit {

  constructor(
    public searchService: SearchService,
    private http: HttpService,
    private authService: AuthService,
    private errorService: ErrorService,
    private snackBarService: SnackBarService,
    private nav: NavigationService,
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
    this.http.postWithPromise('settings/me/monitoring_tags', tempTag).then(res => {
      this.snackBarService.openSnackBar('保存しました', '');
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  cancel() {
    this.nav.toTop();
  }
}