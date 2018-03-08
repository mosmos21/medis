import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { ConvertDateService } from '../services/convert-date.service';
import { AuthService } from '../services/auth.service';
import { ErrorService } from '../services/error.service';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  private templateId: string = "";
  private documentId: string = "";

  public tags: string[] = ["2018年度新人研修", "プログラミング研修"];

  public blocks: any;
  private contentBases: { [key: string]: any } = {};

  public contents: any[] = [];
  public templateValues: { [key: string]: string[] } = {};
  public documentValues: { [key: string]: string[] } = {};

  public comments: any;
  public commentStr: string;

  constructor(
    @Inject('hostname') private hostname: string,
    private http: HttpClient,
    private route: ActivatedRoute,
    public convert: ConvertDateService,
    private authService: AuthService,
    private errorService: ErrorService,
  ) { }

  ngOnInit() {
    this.documentId = this.route.snapshot.paramMap.get('id');

    this.http.get(this.hostname + 'templates/blocks', { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        this.blocks = json;

        for (var b of this.blocks) {
          if (b.additionalType != null) {
            b.offset = b.items.length;
            for (let add of b.addItems) {
              b.items.push(add);
            }
          }
          this.contentBases[b.blockId] = b;
        }

        if (this.documentId == 'new') {
          this.route.fragment.subscribe(id => {
            this.assembleTemplate(id, null);
          });
        } else {
          this.assembleDocument();
        }
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );

    this.http.get(this.hostname + 'documents/' + this.documentId + '/comments', { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        this.comments = json;
      },
      error => {
        this.errorService.errorPath(error.status);
      }
    );
  }

  assembleTemplate(templateId: string, callback: any): void {
    this.templateId = templateId;

    var data;
    this.http.get(this.hostname + 'templates/' + templateId, { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        data = json;
        for (let con of data.contents) {
          var id = this.addBlock(con.blockId);
          for (var i = 0; i < con.items.length - this.contentBases[con.blockId].items.length; i++) {
            this.addItem(id);
          }
          this.templateValues[id] = new Array();
          for (let i of con.items) {
            this.templateValues[id].push(i);
          }
        }
        if (callback != null) {
          callback();
        }
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );

  }

  assembleDocument() {
    var data;
    this.http.get(this.hostname + 'documents/' + this.documentId, { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        data = json;
        this.assembleTemplate(data.templateId, () => {
          for (let i = 0; i < data.contents.length; i++) {
            let id = this.contents[i].id;
            for (let a of data.contents[i].items) {
              this.documentValues[id].push(a);
            }
            while (this.contents[i].items.length < this.documentValues[id].length + this.contents[i].offset) {
              this.addItem(id);
            }
          }
        });
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
  }

  addBlock(id: string): string {
    let c = JSON.parse(JSON.stringify(this.contentBases[id]));
    let newid = 'comp-' + Math.floor(Math.random() * 1000000);
    c.id = newid;
    this.contents.push(c);
    this.templateValues[newid] = new Array();
    this.documentValues[newid] = new Array();
    return newid;
  }

  addItem(target: string): void {
    for (let content of this.contents) {
      if (content.id == target) {
        let items = this.contentBases[content.blockId].addItems;
        for (let add of items) {
          content.items.push(add);
        }
      }
    }
  }

  isChecked(id: string, line: number): boolean {
    return this.documentValues[id][line] == "true";
  }

  submit() {
    var postComment = {
      value: this.commentStr
    }
    this.http.put(this.hostname + "documents/" + this.documentId + "/comments/create", postComment, { withCredentials: true, headers: this.authService.headerAddToken(), responseType: 'text' }).subscribe();
    this.commentStr = "";
  }
}
