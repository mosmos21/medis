import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  private selected_tags = [];
  private seach_word = "";

  // タグ一覧
  private tags = [
    "2017年度新人研修",
    "2018年度新人研修",
    "A-AUTO研修",
    "帳票研修",
    "Waha!研修",
    "研修"
  ];

  constructor() { }

  ngOnInit() { }

  addTags(event: any) {
    if (this.selected_tags.length < 5) {
      var str = event.path[0].innerHTML;
      this.selected_tags.push(str);
      var idx = this.tags.indexOf(str);
      if (idx >= 0) {
        this.tags.splice(idx, 1);
      }
    }
    this.seach_word = "";
  }

  deleteTags(event: any) {
    var str = event.path[0].innerHTML;
    console.log(str);
    this.tags.push(str);
    var idx = this.selected_tags.indexOf(str);
    if (idx >= 0) {
      this.selected_tags.splice(idx, 1);
    }
  }

  searchTag() {
    var targetTag = [];
    var i = this.tags.length;
    while (i--) {
      if (this.tags[i].indexOf(this.seach_word) > -1) {
        targetTag.push(this.tags[i]);
      }
    }
    return targetTag;
  }

}
