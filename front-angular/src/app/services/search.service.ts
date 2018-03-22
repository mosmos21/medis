import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs/Subject';

import { AuthService } from '../services/auth.service';
import { ErrorService } from './error.service';
import { HttpService } from './http.service';
import { TypeConversionService } from './type-conversion.service';
import { TagContent } from '../model/Tag';

@Injectable()
export class SearchService {

  private searchTagsDataSource = new Subject<String>();
  public searchTagsData$ = this.searchTagsDataSource.asObservable();

  public newTagName = "";
  public searchWord = "";
  public selectedTags: TagContent[] = new Array();
  public tempTags: TagContent[] = new Array();
  public newTags: TagContent[] = new Array();
  public tagList: TagContent[] = new Array();
  public targetTags: TagContent[] = new Array();

  constructor(
    private http: HttpService,
    private errorService: ErrorService,
    private typeConversionService: TypeConversionService,
  ) {
    this.init();
  }

  init(): void {
    this.newTagName = "";
    this.searchWord = "";
    this.selectedTags = [];
    this.tempTags = [];
    this.newTags = [];
    this.tagList = [];
    this.targetTags = [];
    this.getTags();
  }

  sendMsg(msg: String): void {
    this.searchTagsDataSource.next(msg);
  }

  getTags(): void {
    this.http.get("tags").subscribe(
      res => {
        this.tagList = this.typeConversionService.makeTagList(res);
        this.targetTags = this.typeConversionService.makeTagList(res);
        this.tempTags = this.typeConversionService.makeTagList(res);
      },
      error => {
        this.errorService.errorPath(error.status);
      }
    );
  }

  getMonitoringTag(): void {
    this.http.get("/settings/me/monitoring_tags").subscribe(
      res => {
        this.selectedTags = this.typeConversionService.makeTagList(res);
        this.excludeTagList(this.selectedTags);
      },
      error => {
        this.errorService.errorPath(error.status);
      }
    );
  }

  excludeTagList(tagArray): void {
    for (let i = 0; i < this.targetTags.length; i++) {
      if (this.checkTagId(tagArray, this.targetTags[i].tagId)) {
        this.targetTags.splice(i, 1);
        this.tempTags.splice(i, 1);
      }
    }
  }

  addTags(event: any, num: number, idx: number): void {
    if (this.selectedTags.length < num) {
      this.selectedTags.push(this.targetTags[idx]);
      this.targetTags.splice(idx, 1);
      this.tempTags.splice(idx, 1);
    }
  }

  deleteTags(event: any, idx: number): void {
    this.targetTags.push(this.selectedTags[idx]);
    this.tempTags.push(this.selectedTags[idx]);
    this.selectedTags.splice(idx, 1);
  }

  addNewTags(event: any): void {
    const str = event.path[0].innerText;
    if (this.selectedTags.length + this.newTags.length < 4 && !this.checkTagName(this.newTags, str)) {
      this.newTags.push({ tagId: "", tagName: str });
    }
  }

  deleteNewTags(idx: number): void {
    this.newTags.splice(idx, 1);
  }

  addTagsToResult(event: any, idx: number): void {
    if (this.selectedTags.length < 5) {
      this.selectedTags.push(this.targetTags[idx]);
      this.targetTags.splice(idx, 1);
      this.tempTags.splice(idx, 1);
    }
    this.sendMsg(this.typeConversionService.makeTagNameList(this.selectedTags).join(","));
  }

  deleteTagsToResult(event: any, idx: number): void {
    this.targetTags.push(this.selectedTags[idx]);
    this.tempTags.push(this.selectedTags[idx]);
    this.selectedTags.splice(idx, 1);
    this.sendMsg(this.typeConversionService.makeTagNameList(this.selectedTags).join(","));
  }

  searchTag(): void {
    this.targetTags = this.tempTags;
    this.targetTags = this.targetTags.filter((item) => {
      if (item.tagName.indexOf(this.searchWord) != -1) {
        return true;
      }
    });
  }

  createNewTag(): string {
    if (!this.checkTagName(this.tagList, this.searchWord)) {
      this.newTagName = this.searchWord;
      return this.newTagName;
    } else {
      return "";
    }
  }

  checkTagName(tagArray: TagContent[], str: string): boolean {
    return tagArray.some(val => val.tagName == str);
  }

  checkTagId(tagArray: TagContent[], id: string): boolean {
    return tagArray.some(val => val.tagId == id);
  }
}
