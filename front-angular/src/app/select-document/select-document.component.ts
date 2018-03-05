import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { AuthService } from '../services/auth.service';
import { NavigationService } from '../services/navigation.service';

@Component({
  selector: 'app-select-document',
  templateUrl: './select-document.component.html',
  styleUrls: ['./select-document.component.css']
})
export class SelectDocumentComponent implements OnInit {

  public title: string = "テンプレート";
  public id: string = "テンプレートID";
  public name: string = "テンプレート名";

  public category: string;
  private list: any;

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private nav: NavigationService,
  ) {
    this.nav.show();
  }

  ngOnInit() {
    this.route.fragment.subscribe(frag => {
      if (frag != null && frag != 'new') {
        this.router.navigate(['']);
      }
      if (frag == null) {
        this.title = "下書き文書";
        this.id = "文書ID";
        this.name = "文書タイトル";
        this.category = 'documents'
      } else {
        this.category = 'templates/public';
      }
      this.loadList();
    });
  }

  loadList(): void {
    this.http.get(this.hostname + this.category, { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
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
