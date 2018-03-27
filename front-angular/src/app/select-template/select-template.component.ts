import { Component, OnInit, Inject, HostListener, ViewChild } from '@angular/core';
import { MatDialog, MatSort } from '@angular/material';

import { ConfirmationComponent } from '../confirmation/confirmation.component';
import { MessageModalComponent } from '../message-modal/message-modal.component';

import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';
import { ErrorService } from '../services/error.service';
import { SnackBarService } from '../services/snack-bar.service';
import { NavigationService } from '../services/navigation.service';
import { ConvertDateService } from '../services/convert-date.service';
import { TableService } from '../services/table.service';

import { TemplateInfo } from '../model/TemplateInfo';

@Component({
  selector: 'app-select-template',
  templateUrl: './select-template.component.html',
  styleUrls: ['./select-template.component.css']
})
export class SelectTemplateComponent implements OnInit {

  public templates: TemplateInfo[] = new Array();
  public dataSource;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    public dialog: MatDialog,
    public convert: ConvertDateService,
    private nav: NavigationService,
    private http: HttpService,
    private authService: AuthService,
    private errorService: ErrorService,
    private snackBarService: SnackBarService,
    public tableService: TableService,
  ) {
    this.nav.showAdminMenu();
    this.nav.show();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.http.get('templates').subscribe(list => {
      this.templates = list;
      console.log(list);
      this.dataSource = this.tableService.insertDataSourceTemplate(list);
      this.dataSource.sort = this.sort;
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  confirmChangePublish(e: any, index: number): void {
    e.preventDefault();
    const enable = this.templates[index]['templatePublish'] ? '非公開に' : '公開';
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        message: this.templates[index]['templateName'] + 'を' + enable + 'します。',
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.templates[index]['templatePublish'] = !this.templates[index]['templatePublish'];
        const type = this.templates[index].templatePublish ? 'public' : 'private';
        const url = 'templates/' + this.templates[index].templateId + '/' + type;
        this.http.post(url, null).subscribe(success => {
          this.snackBarService.openSnackBar('変更しました', '');
        }, error => {
          this.errorService.errorPath(error.status);
        });
      }
    });
  }

  @HostListener('window:unload', ['$event'])
  unloadHandler() {
    this.http.post('users/update', this.templates).subscribe(success => {
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }
}
