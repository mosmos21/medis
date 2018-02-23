import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-config-surveillance',
  templateUrl: './config-surveillance.component.html',
  styleUrls: ['./config-surveillance.component.css']
})
export class ConfigSurveillanceComponent implements OnInit {

  private searchWord = "";
  private tags: any = [
    {
      tagId: "",
      tagName: ""
    }
  ];
  private selectedTags: any = [];

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
  ) { }

  ngOnInit() {
    this.getJSON();
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

  addTags(event: any) {
    var i = this.tags.length;
    var str = event.path[0].innerHTML;
    while (i--) {
      if (this.tags[i]["tagName"] == str) {
        this.selectedTags[this.selectedTags.length] = this.tags[i];
        this.tags.splice(i, 1);
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
    console.log(this.tags);
  }

  getJSON() {
    this.http.get(this.hostname + 'tags').subscribe(
      json => {
        this.tags = json;
      },
      error => {
        this.tags = error;
      }
    );
    this.selectedTags = [];
  }



  save() {
    //保存ボタンが押された時の処理を書く
  }
}
