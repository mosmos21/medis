import { Component, OnInit, Inject } from '@angular/core';

import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';
import { ErrorService } from '../services/error.service';
import { SearchService } from '../services/search.service';
import { NavigationService } from '../services/navigation.service';
import { ConvertDateService } from '../services/convert-date.service';
import { DocumentInfo } from '../model/DocumentInfo';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {

  public documents: DocumentInfo[] = new Array();
  private msg: String;

  constructor(
    private nav: NavigationService,
    private http: HttpService,
    private authService: AuthService,
    private searchService: SearchService,
    private errorService: ErrorService,
    public conv: ConvertDateService,
  ) {
    this.nav.show();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.searchService.searchTagsData$.subscribe(msg => {
      this.msg = msg;
      this.getList(this.msg);
    });
  }

  getList(msg: String): void {
    console.log(msg);
    this.http.get("search?tags=" + this.encodeStringToUri(msg)).subscribe(list => {
      console.log(list);
        this.documents = list;
      },error => {
        this.errorService.errorPath(error.status);
      }
    );
  }

  encodeStringToUri(msg: any): string {
    return encodeURIComponent(msg);
  }

}
