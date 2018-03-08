import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { AuthService } from '../services/auth.service';
import { NavigationService } from '../services/navigation.service';
import { ErrorService } from '../services/error.service';
import { ConvertDateService } from '../services/convert-date.service';

@Component({
  selector: 'app-select-document',
  templateUrl: './select-document.component.html',
  styleUrls: ['./select-document.component.css']
})
export class SelectDocumentComponent implements OnInit {

  public title: string = "下書き文書";
  public id: string = "文書ID";
  public name: string = "文書タイトル";

  public category: string = "documents/private";
  public list: any;

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private errorService: ErrorService,
    private nav: NavigationService,
    public conv: ConvertDateService
  ) {
    this.nav.show();
    this.authService.getUserDetail(http);
  }

  ngOnInit() {
    this.loadList();
  }

  loadList(): void {
    this.http.get(this.hostname + this.category,
      { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
        json => {
          this.list = json;
          console.log(this.list);
        },
        error => {
          this.errorService.errorPath(error.status)
        }
      );
  }

}
