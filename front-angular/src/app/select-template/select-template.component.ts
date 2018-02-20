import { Component, OnInit, Inject, HostListener } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatDialog } from '@angular/material';
import { NavigationService } from '../services/navigation.service';

import { ConfirmationComponent } from '../confirmation/confirmation.component'
import { ConvertDateService } from '../services/convert-date.service';

@Component({
  selector: 'app-select-template',
  templateUrl: './select-template.component.html',
  styleUrls: ['./select-template.component.css']
})
export class SelectTemplateComponent implements OnInit {

  private templates;
  private enable;
  private message;

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    public dialog: MatDialog,
    private nav: NavigationService,
    private convert: ConvertDateService,
  ) {
    this.nav.showAdminMenu();
  }

  confirmChangePublish(e: any, index: number): void {
    e.preventDefault();

    if (!this.templates[index]["isTemplatePublish"]) {
      this.enable = "公開";
    } else {
      this.enable = "非公開に";
    }

    this.message = this.templates[index]['templateName'] + 'を' + this.enable + 'します。'

    let dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        message: this.message
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.templates[index]["isTemplatePublish"] = !this.templates[index]["isTemplatePublish"];
      }
    });
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

  @HostListener('window:unload', ['$event'])
  unloadHandler() {
    this.http.post(this.hostname + "users/update", this.templates).subscribe(
      /* postした時の操作があればここにかく */
    );
  }

}
