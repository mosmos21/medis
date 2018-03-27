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
  public displayedColumnsDocument = ["documentName", "name", "documentCreateDate"];
  public displayedColumnsOwnDocument = ["documentName", "documentCreateDate"];
  public displayedColumnsComment = ["documentName", "updateDate"];
  public displayedColumnsDraft = ["documentName", "documentCreateDate", "delete"];
  public displayedColumnsTemplate = ["templateName", "templateCreateDate"];
  public displayedColumnsTemplateAdmin = ["templateName", "templateCreateDate", "templatePublish"];

  public dataSource;
  @ViewChild(MatSort) sort: MatSort;

  constructor() { }

  insertDataSourceDocument(docSource: DocumentInfo[]): Object {
    return new MatTableDataSource<DocumentInfo>(docSource);
  }

  insertDataSourceTemplate(docSource: TemplateInfo[]): Object {
    return new MatTableDataSource<TemplateInfo>(docSource);
  }

}
