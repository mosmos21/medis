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
import { TagContent } from '../model/TagContent';

@Injectable()
export class TableService {
  public displayedColumnsDocument = ["documentName", "name", "documentCreateDate"];
  public displayedColumnsOwnDocument = ["documentName", "documentCreateDate"];
  public displayedColumnsComment = ["documentName", "updateDate"];
  public displayedColumnsDraft = ["documentName", "documentCreateDate", "delete"];
  public displayedColumnsTemplate = ["templateName", "templateCreateDate"];
  public displayedColumnsTemplateAdmin = ["templateName", "templateCreateDate", "templatePublish"];
  public displayedColumnsTag = ["tagName", "mailNotification", "browserNotification"];

  public dataSource;
  @ViewChild(MatSort) sort: MatSort;

  constructor() { }

  insertDataSourceDocument(documentSource: DocumentInfo[]): Object {
    return new MatTableDataSource<DocumentInfo>(documentSource);
  }

  insertDataSourceTemplate(templateSource: TemplateInfo[]): Object {
    return new MatTableDataSource<TemplateInfo>(templateSource);
  }

  insertDataSourceTag(tagSource: TagContent[]): object {
    return new MatTableDataSource<TagContent>(tagSource);
  }

}
