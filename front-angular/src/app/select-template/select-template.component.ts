import { Component, OnInit, Inject, HostListener } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatDialog } from '@angular/material';
import { NavigationService } from '../services/navigation.service';

import { ConfirmationComponent } from '../confirmation/confirmation.component'
import { MessageModalComponent } from '../message-modal/message-modal.component'
import { ConvertDateService } from '../services/convert-date.service';

@Component({
  selector: 'app-select-template',
  templateUrl: './select-template.component.html',
  styleUrls: ['./select-template.component.css']
})
export class SelectTemplateComponent implements OnInit {

  public templates;
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
    this.nav.show();
  }

  confirmChangePublish(e: any, index: number): void {
    e.preventDefault();

    if (!this.templates[index]["templatePublish"]) {
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
        this.templates[index]["templatePublish"] = !this.templates[index]["templatePublish"];

        let type = this.templates[index].templatePublish ? "public" : "private";
        let url = this.hostname + "templates/" + this.templates[index].templateId + '/' + type;
        this.http.post(url, null).subscribe(
          success => {
            let dialogRef = this.dialog.open(MessageModalComponent, {
              data: {
                message: "変更しました"
              }
            });
          },
          error => {
            // TODO 
          }
        );
      }
    });
  }

  ngOnInit() {
    console.log(this.hostname + 'templates');
    this.http.get(this.hostname + 'templates').subscribe(
      json => {
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
