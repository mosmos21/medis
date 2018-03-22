import { Router } from '@angular/router'
import { Component, OnInit, Inject } from '@angular/core';

import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';
import { ErrorService } from '../services/error.service';
import { IsNewService } from '../services/is-new.service';
import { CookieService } from 'ngx-cookie-service'
import { NavigationService } from '../services/navigation.service';
import { ConvertDateService } from '../services/convert-date.service';

import { User } from '../model/User';

@Component({
  selector: 'app-top',
  templateUrl: './top.component.html',
  styleUrls: ['./top.component.css']
})
export class TopComponent implements OnInit {

  public updateList: DocumentInfo[] = new Array();
  public ownDocList: DocumentInfo[] = new Array();
  public favDocList: DocumentInfo[] = new Array();
  public monDocList: DocumentInfo[] = new Array();

  private user: User;

  constructor(
    public conv: ConvertDateService,
    public isNewService: IsNewService,
    private nav: NavigationService,
    private http: HttpService,
    private router: Router,
    private authService: AuthService,
    private errorService: ErrorService,
  ) {
    this.nav.show();
    this.nav.showUserMenu();
    this.user = this.authService.user;
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.http.get("infomations").subscribe(list => {
      this.updateList = list;
    }, error => {
      this.errorService.errorPath(error.status);
    });
    this.http.get("documents/public").subscribe(list => {
      this.ownDocList = list;
    }, error => {
      this.errorService.errorPath(error.status);
    });
    this.http.get("documents/bookmark").subscribe(list => {
      this.favDocList = list;
    }, error => {
      this.errorService.errorPath(error.status);
    });
    this.http.get("documents/monitoring_tags").subscribe(list => {
      this.monDocList = list;
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  viewDocument(id: string): void {
    this.router.navigate(["browsing/" + id]);
  }
}

interface DocumentInfo {
  documentCreateDate: string,
  documentId: string,
  documentName: string,
  documentPublish: boolean,
  employeeNumber: string,
  firstName: string,
  lastName: string,
  templateId: string,
}