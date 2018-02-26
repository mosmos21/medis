import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class SearchService {



  private searchTagsDataSource = new Subject<string>();
  public searchTagsData$ = this.searchTagsDataSource.asObservable();

  public newTag = "";
  public selectedTags: any = [];
  public searchWord: string = "";
  private tags: any = [
    {
      tagId: '',
      tagName: ''
    }
  ];

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
  ) { }

  sendMsg(msg: any) {
    this.searchTagsDataSource.next(msg);
  }

  getTags() {
    this.http.get(this.hostname + 'tags').subscribe(
      json => {
        this.tags = json;
      },
      error => {
        this.tags = error;
      }
    );
  }

  addTags(event: any) {
    if (this.selectedTags.length < 5) {
      var i = this.tags.length;
      var str = event.path[0].innerHTML;
      while (i--) {
        if (this.tags[i]["tagName"] == str) {
          this.selectedTags[this.selectedTags.length] = this.tags[i];
          this.tags.splice(i, 1);
        }
      }
    }
  }

  deleteTags(event: any) {
    var i = this.selectedTags.length;
    var str = event.path[0].innerHTML;
    while (i--) {
      if (this.selectedTags[i]["tagName"] == str) {
        this.tags[this.tags.length] = this.selectedTags[i];
        this.selectedTags.splice(i, 1);
      }
    }
  }

  addTagsToResult(event: any) {
    if (this.selectedTags.length < 5) {
      var i = this.tags.length;
      var str = event.path[0].innerHTML;
      while (i--) {
        if (this.tags[i]["tagName"] == str) {
          this.selectedTags[this.selectedTags.length] = this.tags[i];
          this.sendMsg(this.selectedTags);
          this.tags.splice(i, 1);
        }
      }
    }
  }

  deleteTagsToResult(event: any) {
    var i = this.selectedTags.length;
    var str = event.path[0].innerHTML;
    while (i--) {
      if (this.selectedTags[i]["tagName"] == str) {
        this.tags[this.tags.length] = this.selectedTags[i];
        this.sendMsg(this.selectedTags);
        this.selectedTags.splice(i, 1);
      }
    }
  }

  searchTag() {
    var sub = JSON.stringify(this.tags);
    var targetTags = JSON.parse(sub);
    var i = targetTags.length;
    while (i--) {
      if (this.tags[i]["tagName"].indexOf(this.searchWord) == -1) {
        targetTags.splice(i, 1);
      }
    }
    console.log(targetTags);
    return targetTags;
  }

  addNewTag() {
    var i = this.tags.length;
    var count = 0;
    while (i--) {
      if (this.tags[i]["tagName"] == this.searchWord) {
        count++;
      }
    }
    count == 0 ? this.newTag = this.searchWord : this.newTag = "";
    return this.newTag;
  }
}
