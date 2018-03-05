import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs/Subject';

import { AuthService } from '../services/auth.service';

@Injectable()
export class SearchService {



  private searchTagsDataSource = new Subject<string>();
  public searchTagsData$ = this.searchTagsDataSource.asObservable();

  public newTagName = "";
  public selectedTags = [];
  private tempTags = [];
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
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private authService: AuthService,
  ) { }

  sendMsg(msg: any) {
    this.searchTagsDataSource.next(msg);
  }

  getTags() {
    this.http.get(this.hostname + 'tags', { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        this.tags = json;
        const sub = JSON.stringify(this.tags);
        this.targetTags = JSON.parse(sub);
        this.tempTags = JSON.parse(sub);
        console.log(this.tags);
      },
      error => {
        this.tags = error;
      }
    );
  }

  addTags(event: any, num: number) {
    console.log("add");
    if (this.selectedTags.length + this.newTags.length < num) {
      var i = this.targetTags.length;
      var str = event.path[0].innerText;
      while (i--) {
        if (this.targetTags[i]["tagName"] == str) {
          this.selectedTags[this.selectedTags.length] = this.targetTags[i];
          this.targetTags.splice(i, 1);
        }
      }

      let j = this.tempTags.length;
      while (j--) {
        if (this.tempTags[j]["tagName"] == str) {
          this.tempTags.splice(j, 1);
          console.log(this.tempTags);
        }
      }
    }
  }

  deleteTags(event: any) {
    console.log("delete");
    var i = this.selectedTags.length;
    var str = event.path[0].innerText;
    while (i--) {
      if (this.selectedTags[i]["tagName"] == str) {
        this.targetTags[this.targetTags.length] = this.selectedTags[i];
        this.tempTags[this.tempTags.length] = this.selectedTags[i];
        this.selectedTags.splice(i, 1);
      }
    }
  }

  addNewTags(event: any) {
    console.log("addNew");
    if (this.selectedTags.length + this.newTags.length < 4) {
      var str = event.path[0].innerText;
      var i = this.newTags.length;
      if (i == 0) {
        this.newTags[this.newTags.length] = {
          tagId: "",
          tagName: str
        };
      } else {
        var count = 0;
        while (i--) {
          if (this.newTags[i]["tagName"] == str) {
            count++;
          }
        }
        count == 0 ? this.newTags[this.newTags.length] = { tagId: "", tagName: str } : false;
      }
    }
  }

  deleteNewTags(event: any) {
    console.log("deleteNew");
    var i = this.newTags.length;
    var str = event.path[0].innerText;
    while (i--) {
      if (this.newTags[i]["tagName"] == str) {
        this.newTags.splice(i, 1);
      }
    }
  }

  addTagsToResult(event: any) {
    console.log("add");
    if (this.selectedTags.length < 5) {
      var i = this.targetTags.length;
      var str = event.path[0].innerText;
      while (i--) {
        if (this.targetTags[i]["tagName"] == str) {
          this.selectedTags[this.selectedTags.length] = this.targetTags[i];
          this.targetTags.splice(i, 1);
          this.sendMsg(this.selectedTags);
        }
      }

      let j = this.tempTags.length;
      while (j--) {
        if (this.tempTags[j]["tagName"] == str) {
          this.tempTags.splice(j, 1);
          console.log(this.tempTags);
        }
      }
      console.log(this.selectedTags);
    }
  }

  deleteTagsToResult(event: any) {
    console.log("delete");
    var i = this.selectedTags.length;
    var str = event.path[0].innerText;
    while (i--) {
      if (this.selectedTags[i]["tagName"] == str) {
        this.targetTags[this.targetTags.length] = this.selectedTags[i];
        this.tempTags[this.tempTags.length] = this.selectedTags[i];
        this.sendMsg(this.selectedTags);
        this.selectedTags.splice(i, 1);
      }
    }
  }

  searchTag() {
    console.log("search");
    const sub = JSON.stringify(this.tempTags);
    this.targetTags = JSON.parse(sub);
    let i = this.targetTags.length;
    while (i--) {
      if (this.targetTags[i]["tagName"].indexOf(this.searchWord) == -1) {
        this.targetTags.splice(i, 1);
      }
    }
    return this.targetTags;
  }

  createNewTag() {
    var i = this.tags.length;
    var count = 0;
    while (i--) {
      if (this.tags[i]["tagName"] == this.searchWord) {
        count++;
      }
    }
    count == 0 ? this.newTagName = this.searchWord : this.newTagName = "";
    return this.newTagName;
  }
}
