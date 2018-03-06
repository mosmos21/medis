import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { AuthService } from '../services/auth.service';
import { NavigationService } from '../services/navigation.service';

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
  ) {
    this.nav.show();
  }

  ngOnInit() {
    this.loadlist();
  }

  loadlist(): void {
    this.http.get(this.hostname + "templates/public",
      { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
        json => {
          this.list = json;
          console.log(this.list);
        },
        error => {
          // TODO;
        }
      );
  }

}
