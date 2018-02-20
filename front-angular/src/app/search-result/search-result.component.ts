import { Component, OnInit, Inject } from '@angular/core';
import { NavigationService } from '../services/navigation.service';
import { HttpClient } from '@angular/common/http';
import { SearchService } from '../services/search.service';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {

  private title: string = "検索結果";
  private id: string = "ドキュメントID";
  private name: string = "ドキュメント名";
  private list: any;
  private msg: string;

  constructor(
    private nav: NavigationService,
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private searchService: SearchService,
  ) { }

  ngOnInit() {
    this.searchService.searchTagsData$.subscribe(
      msg => {
        this.msg = msg;
        console.log(msg);
        msg.length > 0 ? this.getList(msg) : this.list = [];
      }
    );
  }

  getList(msg: any): void {
    console.log(this.hostname + "search?tags=" + this.encodeStringToUri(msg));
    this.http.get(this.hostname + "search?tags=" + this.encodeStringToUri(msg)).subscribe(
      json => {
        this.list = json;
      },
      error => {
        // TODO;
      }
    );
  }

  encodeStringToUri(msg: any) {
    var str = msg.join(",");
    var encord_tag = encodeURIComponent(str);
    return encord_tag;
  }

}
