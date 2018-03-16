import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router'

import { AuthService } from '../services/auth.service';
import { CookieService } from 'ngx-cookie-service'
import { ErrorService } from '../services/error.service';
import { ConvertDateService } from '../services/convert-date.service';
import { NavigationService } from '../services/navigation.service';
import { IsNewService } from '../services/is-new.service';

@Component({
  selector: 'app-top',
  templateUrl: './top.component.html',
  styleUrls: ['./top.component.css']
})
export class TopComponent implements OnInit {

  public updateList: any;
  public ownDocList: any;
  public favDocList: any;
  public monDocList: any;

  private user: any;

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private router: Router,
    private nav: NavigationService,
    private authService: AuthService,
    private cookieService: CookieService,
    private errorService: ErrorService,
    public conv: ConvertDateService,
    public isNewService: IsNewService,
  ) {
    this.nav.show();
    this.nav.showUserMenu();
    this.user = this.authService.user;
    this.authService.getUserDetail(http);
    // console.log(this.user);
  }

  ngOnInit() {
    this.http.get(this.hostname + "infomations", { headers: this.authService.headerAddToken(), withCredentials: true }).subscribe(
      json => {
        this.updateList = json;
        // console.log(this.updateList);
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
    this.http.get(this.hostname + "documents/public", { headers: this.authService.headerAddToken(), withCredentials: true }).subscribe(
      json => {
        this.ownDocList = json;
        // console.log(this.updateList);
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
    this.http.get(this.hostname + "documents/bookmark", { headers: this.authService.headerAddToken(), withCredentials: true }).subscribe(
      json => {
        this.favDocList = json;
        // console.log(this.updateList);
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
    this.http.get(this.hostname + "documents/monitoring_tags", { headers: this.authService.headerAddToken(), withCredentials: true }).subscribe(
      json => {
        this.monDocList = json;
        // console.log(this.updateList);
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
  }

  viewDocument(id: string) {
    this.router.navigate(["browsing/" + id]);
  }

}
