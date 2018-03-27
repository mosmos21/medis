import { HttpClient } from '@angular/common/http';
import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import {
  MatSort,
  MatTableDataSource
} from '@angular/material';

import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';
import { ErrorService } from '../services/error.service';
import { NavigationService } from '../services/navigation.service';
import { ConvertDateService } from '../services/convert-date.service';
import { TableService } from '../services/table.service';

import { TemplateInfo } from '../model/TemplateInfo';

@Component({
  selector: 'app-new-document',
  templateUrl: './new-document.component.html',
  styleUrls: ['./new-document.component.css']
})
export class NewDocumentComponent implements OnInit {

  public templates: TemplateInfo[] = new Array();
  public dataSource;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    public conv: ConvertDateService,
    private nav: NavigationService,
    private http: HttpService,
    private authService: AuthService,
    private errorService: ErrorService,
    public tableService: TableService,
  ) {
    this.nav.show();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.loadlist();
  }

  loadlist(): void {
    this.http.get("templates/public").subscribe(res => {
      // this.templates = res;
      console.log(res);
      this.dataSource = this.tableService.insertDataSourceTemplate(res);
      this.dataSource.sort = this.sort;
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }
}
