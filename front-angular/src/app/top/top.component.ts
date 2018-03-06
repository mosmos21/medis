import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NavigationService } from '../services/navigation.service';

import { AuthService } from '../services/auth.service';
import { CookieService } from 'ngx-cookie-service'
import { ErrorService } from '../services/error.service';

@Component({
  selector: 'app-top',
  templateUrl: './top.component.html',
  styleUrls: ['./top.component.css']
})
export class TopComponent implements OnInit {

  public updateList: any;
  private ownDocList: any;
  private favDocList: any;
  private MonDocList: any;

  private user: any;

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private nav: NavigationService,
    private authService: AuthService,
    private cookieService: CookieService,
    private errorService: ErrorService,
  ) {
    this.nav.show();
    this.user = this.authService.user;
    console.log(this.user);
  }

  ngOnInit() {
    this.http.get(this.hostname + "infomations", { headers: this.authService.headerAddToken(), withCredentials: true }).subscribe(
      json => {
        this.updateList = json;
        console.log(this.updateList);
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
  }

}
