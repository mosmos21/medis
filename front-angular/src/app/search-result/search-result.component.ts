import { Component, OnInit, Inject } from '@angular/core';
import { NavigationService } from '../services/navigation.service';
import { HttpClient } from '@angular/common/http';

import { AuthService } from '../services/auth.service';
import { SearchService } from '../services/search.service';
import { ErrorService } from '../services/error.service';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {

  public title: string = "検索結果";
  public id: string = "ドキュメントID";
  public name: string = "ドキュメント名";
  public list: any;
  private msg: string;

  constructor(
    private nav: NavigationService,
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private authService: AuthService,
    private searchService: SearchService,
    private errorService: ErrorService,
  ) {
    this.nav.show();
  }

  ngOnInit() {
    this.searchService.searchTagsData$.subscribe(
      msg => {
        this.msg = msg;
        msg.length > 0 ? this.getList(msg) : this.list = [];
      }
    );
  }

  getList(msg: any): void {
    console.log(this.hostname + "search?tags=" + this.encodeStringToUri(msg));
    this.http.get(this.hostname + "search?tags=" + this.encodeStringToUri(msg), { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        this.list = json;
        console.log(this.list);
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
  }

  encodeStringToUri(msg: any) {
    var str = msg.join(",");
    var encord_tag = encodeURIComponent(str);
    return encord_tag;
  }

}
