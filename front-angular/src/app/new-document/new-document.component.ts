import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { AuthService } from '../services/auth.service';
import { NavigationService } from '../services/navigation.service';
import { ConvertDateService } from '../services/convert-date.service';

@Component({
  selector: 'app-new-document',
  templateUrl: './new-document.component.html',
  styleUrls: ['./new-document.component.css']
})
export class NewDocumentComponent implements OnInit {

  public list;
  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private nav: NavigationService,
    private authService: AuthService,
    public conv: ConvertDateService
  ) {
    this.nav.show();
    this.authService.getUserDetail(http);
  }

  ngOnInit() {
    this.loadlist();
  }

  loadlist(): void {
    this.http.get(this.hostname + "templates/public",
      { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
        json => {
          this.list = json;
          console.log(this.conv.time2Date(this.list[0].templateCreateDate));
        },
        error => {
          // TODO;
        }
      );
  }

}
