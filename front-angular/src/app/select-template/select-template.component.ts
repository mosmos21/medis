import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NavigationService } from '../services/navigation.service';

@Component({
  selector: 'app-select-template',
  templateUrl: './select-template.component.html',
  styleUrls: ['./select-template.component.css']
})
export class SelectTemplateComponent implements OnInit {

  private templates;

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private nav: NavigationService
  ) {
    this.nav.adminMenu();
  }

  ngOnInit() {
    console.log(this.hostname + 'templates');
    this.http.get(this.hostname + 'templates').subscribe(
      json => {
        console.log(json);
        this.templates = json;
      },
      error => {
        this.templates = error;
      }
    );
  }

}
