import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NavigationService } from '../services/navigation.service';

import { AuthService } from '../services/auth.service';
import { CookieService } from 'ngx-cookie-service'

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
<<<<<<< HEAD
    private nav: NavigationService
  ) {
    this.nav.show();
=======
    private authService: AuthService,
    private cookieService: CookieService,
  ) {
    this.user = this.authService.user;
>>>>>>> 72405627be13dd2334fec35e66869970f1ae6fa0
  }

  ngOnInit() {
    var employeeNumber = this.user.employeeNumber;
    this.http.get(this.hostname + "infomations/" + employeeNumber, { headers: this.authService.headerAddToken(), withCredentials: true }).subscribe(
      json => {
        this.updateList = json;
        console.log(this.updateList);
      },
      error => {
        console.log(error.headers)
      }
    );
  }

}
