import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { MatTableDataSource } from '@angular/material';
import { MatSort } from '@angular/material';
import { Router } from '@angular/router';

import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';
import { ErrorService } from '../services/error.service';
import { SearchService } from '../services/search.service';
import { NavigationService } from '../services/navigation.service';
import { ConvertDateService } from '../services/convert-date.service';
import { TableService } from '../services/table.service';
import { TypeConversionService } from '../services/type-conversion.service';
import { DocumentInfo } from '../model/DocumentInfo';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {

  public documents: DocumentInfo[] = new Array();
  private msg: String;
  public dataSource;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private nav: NavigationService,
    private http: HttpService,
    private authService: AuthService,
    private searchService: SearchService,
    private errorService: ErrorService,
    public conv: ConvertDateService,
    public tableService: TableService,
    private router: Router,
    private typeConv: TypeConversionService,
  ) {
    this.nav.show();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    console.log(this.searchService.selectedTags);
    if (this.searchService.selectedTags.length > 0) {
      this.getList(this.typeConv.makeTagNameList(this.searchService.selectedTags).join(","));
    } else {
      this.dataSource = "";
    }
    this.searchService.searchTagsData$.subscribe(msg => {
      this.msg = msg;
      this.getList(this.msg);
    });
  }

  getList(msg: String): void {
    this.http.get("search?tags=" + this.encodeStringToUri(msg)).subscribe(list => {
      this.dataSource = this.tableService.insertDataSourceDocument(list);
      this.dataSource.sort = this.sort;
    }, error => {
      this.errorService.errorPath(error.status);
    }
    );
  }

  encodeStringToUri(msg: any): string {
    return encodeURIComponent(msg);
  }

  viewDocument(documentId: string) {
    this.router.navigate(["browsing/" + documentId]);
  }
}
