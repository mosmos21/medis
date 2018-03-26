import { Router } from '@angular/router'
import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { MatTableDataSource, MatSort } from '@angular/material';

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

  public displayedColumnsNU = ["documentName", "updateDate"];
  public displayedColumnsND = ["documentName", "documentCreateDate"];
  public displayedColumnsNND = ["documentName", "name", "documentCreateDate"];
  public commentDataSource;
  public ownDataSource;
  public favDataSource;
  public monitoringDataSource;
  @ViewChild('commentTable', { read: MatSort }) commentSort: MatSort;
  @ViewChild('ownTable', { read: MatSort }) ownSort: MatSort;
  @ViewChild('favTable', { read: MatSort }) favSort: MatSort;
  @ViewChild('monitoringTable', { read: MatSort }) monitoringSort: MatSort;

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
      this.commentDataSource = new MatTableDataSource<DocumentInfo>(list);
      this.commentDataSource.sort = this.commentSort;
    }, error => {
      this.errorService.errorPath(error.status);
    });
    this.http.get("documents/public").subscribe(list => {
      this.ownDocList = list;
      this.ownDataSource = new MatTableDataSource<DocumentInfo>(list);
      this.ownDataSource.sort = this.ownSort;
    }, error => {
      this.errorService.errorPath(error.status);
    });
    this.http.get("documents/bookmark").subscribe(list => {
      this.favDocList = list;
      this.favDataSource = new MatTableDataSource<DocumentInfo>(list);
      this.favDataSource.sort = this.favSort;
    }, error => {
      this.errorService.errorPath(error.status);
    });
    this.http.get("documents/monitoring_tags").subscribe(list => {
      this.monDocList = list;
      this.monitoringDataSource = new MatTableDataSource<DocumentInfo>(list);
      this.monitoringDataSource.sort = this.monitoringSort;
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