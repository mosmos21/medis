import {
  Injectable,
  ViewChild,
} from '@angular/core';
import {
  MatSort,
  MatTableDataSource,
} from '@angular/material';

import { DocumentInfo } from '../model/DocumentInfo';
import { TemplateInfo } from '../model/TemplateInfo';

@Injectable()
export class TableService {
  public displayedColumnsDocument = ["documentId", "documentName", "name", "documentCreateDate"];
  public displayedColumnsTemplate = ["templateId", "templateName", "templateCreateDate"];
  public dataSource;
  @ViewChild(MatSort) sort: MatSort;

  constructor() { }

  insertDataSourceDocument(docSource: DocumentInfo[]): Object {
    this.dataSource = new MatTableDataSource<DocumentInfo>(docSource);
    return this.dataSource;
  }

  insertDataSourceTemplate(docSource: TemplateInfo[]): Object {
    this.dataSource = new MatTableDataSource<TemplateInfo>(docSource);
    return this.dataSource;
  }

}
