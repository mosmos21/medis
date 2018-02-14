import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  selected_tags = [];
  seach_word = "";

  // タグ一覧
  tags = [
    "2017年度新人研修",
    "2018年度新人研修",
    "A-AUTO研修",
    "帳票研修",
    "Waha!研修",
    "研修"
  ];

  constructor() { }

  ngOnInit() { }

  clickEvent(event: any) {
    if (this.selected_tags.length < 5) {
      this.selected_tags.push(event.path[0].innerHTML);
    }
    // console.log(this.selected_tags);
  }

  searchTag() {
    const targetTag = this.tags.filter(tag => tag == this.seach_word);
    return targetTag;
  }
}
