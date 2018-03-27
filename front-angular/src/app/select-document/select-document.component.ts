import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
// import { HttpClient } from '@angular/common/http';
import {
  MatSort,
  MatTableDataSource,
} from '@angular/material';

import { AuthService } from '../services/auth.service';
import { NavigationService } from '../services/navigation.service';
import { ErrorService } from '../services/error.service';
import { ConvertDateService } from '../services/convert-date.service';
import { SnackBarService } from '../services/snack-bar.service';
import { MsgToSidenavService } from '../services/msg-to-sidenav.service';
import { HttpService } from '../services/http.service';
import { TableService } from '../services/table.service';
import { DocumentInfo } from '../model/DocumentInfo';

@Component({
  selector: 'app-select-document',
  templateUrl: './select-document.component.html',
  styleUrls: ['./select-document.component.css']
})
export class SelectDocumentComponent implements OnInit {

  public documents: DocumentInfo[] = new Array();
  public dataSource;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    public conv: ConvertDateService,
    private nav: NavigationService,
    private http: HttpService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private errorService: ErrorService,
    private snackBarService: SnackBarService,
    private msgToSidenavService: MsgToSidenavService,
    public tableService: TableService,
  ) {
    this.nav.show();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.loadList();
  }

  loadList(): void {
    this.http.get('documents/private').subscribe(res => {
      this.dataSource = this.tableService.insertDataSourceDocument(res);
      this.dataSource.sort = this.sort;
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  deleteDraft(documentId: string) {
    this.http.delete('documents/' + documentId, ).subscribe(success => {
      this.snackBarService.openSnackBar('下書きを削除しました', '');
      this.loadList();
      this.msgToSidenavService.sendMsg();
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  editDocument(documentId: string) {
    this.router.navigate(["edit/" + documentId]);
  }
}
