import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { SearchService } from '../services/search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  private selected_tags = [];
  private temp_tags = [];
  private seach_word = "";
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
        var i = this.tags.length;
        while (i--) {
          this.temp_tags.push(this.tags[i]["tagName"]);
        }
      },
      error => {
        this.tags = error;
      }
    );
  }

  addTags(event: any) {
    if (this.selected_tags.length < 5) {
      var str = event.path[0].innerHTML;
      this.selected_tags.push(str);
      this.msg = this.selected_tags;
      this.sendMsgToResult(this.msg);
      var i = this.temp_tags.length;
      while (i--) {
        if (this.temp_tags[i] == str) {
          this.temp_tags.splice(i, 1);
        }
      }
    }
    this.seach_word = "";
  }

  deleteTags(event: any) {
    var str = event.path[0].innerHTML;
    this.temp_tags.push(str);
    var idx = this.selected_tags.indexOf(str);
    if (idx >= 0) {
      this.selected_tags.splice(idx, 1);
      this.msg = this.selected_tags;
      this.sendMsgToResult(this.msg);
    }
  }

  searchTag() {
    console.log(this.seach_word);
    var i = this.temp_tags.length;
    var str = this.seach_word;
    var target_tags: any = [];
    while (i--) {
      if (this.temp_tags[i].indexOf(str) > -1) {
        var temp_str = this.temp_tags[i];
        target_tags.push(temp_str);
      }
    }
    return target_tags;
  }

  sendMsgToResult(msg: any) {
    this.searchService.sendMsg(msg);
  }
}
