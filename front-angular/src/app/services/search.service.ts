import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs/Subject';

import { AuthService } from '../services/auth.service';
import { ErrorService } from './error.service';
import { HttpService } from './http.service';

@Injectable()
export class SearchService {

  private searchTagsDataSource = new Subject<String>();
  public searchTagsData$ = this.searchTagsDataSource.asObservable();

  public newTagName = "";
  public selectedTags = [];
  public tempTags = [];
  public newTags = [];
  public searchWord = "";
  private tags: any = [
    {
      tagId: "",
      tagName: ""
    }
  ];
  public targetTags: any = [
    {
      tagId: "",
      tagName: ""
    }
  ];

  constructor(
    private http: HttpService,
    private errorService: ErrorService,
  ) {
    this.init();
  }

  init(): void {
    this.newTagName = "";
    this.selectedTags = [];
    this.tempTags = [];
    this.newTags = [];
    this.searchWord = "";
    this.tags = [
      {
        tagId: "",
        tagName: ""
      }
    ];
    this.targetTags = [
      {
        tagId: "",
        tagName: ""
      }
    ];
    this.getTags();
  }

  sendMsg(msg: String) {
    this.searchTagsDataSource.next(msg);
  }

  getTags() {
    this.http.get('tags').subscribe(
      json => {
        this.tags = json;
        const sub = JSON.stringify(this.tags);
        this.targetTags = JSON.parse(sub);
        this.tempTags = JSON.parse(sub);
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
  }

  getMonitoringTag() {
    this.http.get('/settings/me/monitoring_tags').subscribe(
      json => {
        const temp = json;
        const sub = JSON.stringify(temp);
        this.selectedTags = JSON.parse(sub);
        // console.log(json);
        var i = this.selectedTags.length;
        while (i--) {
          var j = this.targetTags.length;
          while (j--) {
            if (this.selectedTags[i].tagId == this.targetTags[j].tagId) {
              this.targetTags.splice(j, 1);
              this.tempTags.splice(j, 1);
              break;
            }
          }
        }
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
  }

  addTags(event: any, num: number, idx: number) {
    this.selectedTags.push(this.targetTags[idx]);
    this.targetTags.splice(idx, 1);
    this.tempTags.splice(idx, 1);
  }

  deleteTags(event: any, idx: number) {
    this.targetTags.push(this.selectedTags[idx]);
    this.tempTags.push(this.selectedTags[idx]);
    this.selectedTags.splice(idx, 1);
  }

  addNewTags(event: any) {
    if (this.selectedTags.length + this.newTags.length < 4) {
      let i = this.newTags.length;
      let count = 0;
      while (i--) {
        this.newTags[i].tagName == event.path[0].innerText ? count++ : false;
      }
      count == 0 ? this.newTags.push({ tagId: "", tagName: event.path[0].innerText }) : false;
    }
  }

  deleteNewTags(idx: number) {
    this.newTags.splice(idx, 1);
  }

  addTagsToResult(event: any, idx: number) {
    if (this.selectedTags.length < 5) {
      this.selectedTags.push(this.targetTags[idx]);
      this.targetTags.splice(idx, 1);
      this.tempTags.splice(idx, 1);
    }
    let i = this.selectedTags.length;
    let tagList = [];
    while (i--) {
      tagList.push(this.selectedTags[i].tagName);
    }
    this.sendMsg(tagList.join(","));
  }

  deleteTagsToResult(event: any, idx: number) {
    this.targetTags.push(this.selectedTags[idx]);
    this.tempTags.push(this.selectedTags[idx]);
    this.selectedTags.splice(idx, 1);
    let i = this.selectedTags.length;
    let tagList = [];
    while (i--) {
      tagList.push(this.selectedTags[i].tagName);
    }
    this.sendMsg(tagList.join(","));
  }

  searchTag() {
    console.log("search");
    const sub = JSON.stringify(this.tempTags);
    this.targetTags = JSON.parse(sub);
    this.targetTags = this.targetTags.filter((item, index) => {
      if (item.tagName.indexOf(this.searchWord) != -1) {
        return true;
      }
    });
  }

  createNewTag() {
    var i = this.tags.length;
    var count = 0;
    while (i--) {
      this.tags[i].tagName == this.searchWord ? count++ : false;
    }
    count == 0 ? this.newTagName = this.searchWord : this.newTagName = "";
    return this.newTagName;
  }
}
