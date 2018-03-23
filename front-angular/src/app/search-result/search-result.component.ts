import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { AsyncPipe } from '@angular/common';

import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';
import { ErrorService } from '../services/error.service';
import { SearchService } from '../services/search.service';
import { NavigationService } from '../services/navigation.service';
import { ConvertDateService } from '../services/convert-date.service';
import { DocumentInfo } from '../model/DocumentInfo';
import { MatTableDataSource } from '@angular/material';
import { MatSort } from '@angular/material';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {

  public documents: DocumentInfo[] = new Array();
  private msg: String;
  public displayedColumns = ["documentId", "documentName", "creatorName", "createDate"];
  public dataSource;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private nav: NavigationService,
    private http: HttpService,
    private authService: AuthService,
    private searchService: SearchService,
    private errorService: ErrorService,
    public conv: ConvertDateService,
  ) {
    this.nav.show();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.searchService.searchTagsData$.subscribe(msg => {
      this.msg = msg;
      this.getList(this.msg);
    });
  }

  // ngAfterViewInit() {
  //   this.dataSource.sort = this.sort;
  // }

  getList(msg: String): void {
    console.log(msg);
    this.http.get("search?tags=" + this.encodeStringToUri(msg)).subscribe(list => {
      this.documents = list;
      this.dataSource = new MatTableDataSource<DocumentInfo>(this.documents);
      this.dataSource.sort = this.sort;
      console.log(this.dataSource);
    }, error => {
      this.errorService.errorPath(error.status);
    }
    );
  }

  existDocuments(): boolean {
    return Object.keys(this.documents).length > 1;
  }

  encodeStringToUri(msg: any): string {
    return encodeURIComponent(msg);
  }

}
