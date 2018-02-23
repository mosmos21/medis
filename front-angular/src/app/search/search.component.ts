import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { SearchService } from '../services/search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  private selectedTags: any = [];
  private temp_tags = [];
  private searchWord = "";
  private tags: any = [
    {
      tagId: '',
      tagName: ''
    }
  ];
  private msg = [];

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private searchService: SearchService,
  ) { }

  ngOnInit() {
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
          this.sendMsgToResult(this.selectedTags);
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
        this.sendMsgToResult(this.selectedTags);
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
    return targetTags;
  }

  sendMsgToResult(msg: any) {
    this.searchService.sendMsg(msg);
  }
}
