import { HttpClient } from '@angular/common/http';
import { Component, OnInit, Inject } from '@angular/core';

import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';
import { ErrorService } from '../services/error.service';
import { NavigationService } from '../services/navigation.service';
import { ConvertDateService } from '../services/convert-date.service';

import { TemplateInfo } from '../model/TemplateInfo';

@Component({
  selector: 'app-new-document',
  templateUrl: './new-document.component.html',
  styleUrls: ['./new-document.component.css']
})
export class NewDocumentComponent implements OnInit {

  public templates: TemplateInfo[] = new Array();

  constructor(
    public conv: ConvertDateService,
    private nav: NavigationService,
    private http: HttpService,
    private authService: AuthService,
    private errorService: ErrorService
  ) {
    this.nav.show();
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.loadlist();
  }

  loadlist(): void {
    this.http.get("templates/public").subscribe(res => {
      this.templates = res;
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }
}
