import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
  private contentBases: { [key: string]: any } = {};

  public documentName: string;
  public employeeNumber: string;
  public documentCreateDate: number;
  public name: string;
  public tags: string[] = [];
  public blocks: any;
  public contents: any[] = [];
  public templateValues: { [key: string]: string[] } = {};
  public documentValues: { [key: string]: string[] } = {};

  public comments: any;
  public commentStr: string;

  public isFav: boolean;

  constructor(
    public convert: ConvertDateService,
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private errorService: ErrorService,
    @Inject('hostname') private hostname: string,
  ) {
    this.authService.getUserDetail(http);
  }

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
      },
      error => {
        this.errorService.errorPath(error.status)
      }
    );
    this.assembleDocument(this.documentId);
    this.getComments();
  }

  isMyDocument(): boolean {
    return this.authService.userdetail.employeeNumber == this.employeeNumber;
  }

  goEdit(): void {
    this.router.navigate(['/edit/' + this.documentId]);
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

  assembleDocument(documentId) {
    var data;
    this.http.get(this.hostname + 'documents/' + this.documentId, { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        data = json;
        this.documentName = data.documentName;
        this.employeeNumber = data.employeeNumber;
        this.documentCreateDate = data.documentCreateDate;
        this.name = data.name;
        this.isFav = data.selected
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
        this.getTags(data.templateId, documentId);
      },
      error => {
        this.errorService.errorPath(error.status);
      }
    )
  }

  getTags(templateId: string, documentId: string): void {
    var instantTags: any;
    this.http.get(this.hostname + 'templates/' + templateId + "/tags", { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        instantTags = json;
        for (let t of instantTags) {
          this.tags.push(t.tagName);
        }
      },
      error => {
        this.errorService.errorPath(error.status);
      }
    );
    this.http.get(this.hostname + 'documents/' + documentId + "/tags", { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        instantTags = json;
        for (let t of instantTags) {
          this.tags.push(t.tagName);
        }
      },
      error => {
        this.errorService.errorPath(error.status);
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
    this.http.put(this.hostname + "documents/" + this.documentId + "/comments/create", postComment, { withCredentials: true, headers: this.authService.headerAddToken(), responseType: 'text' }).subscribe(
      json => {
        let data = JSON.parse(json);
        this.comments.push(data);
        for (let c of this.comments) {
          if (c.read) {
            c['alreadyRead'] = 'read'
          } else if (this.employeeNumber == this.authService.userdetail.employeeNumber) {
            c['alreadyRead'] = 'unreadMe'
          } else {
            c['alreadyRead'] = 'unread'
          }
        }
      },
      error => {
        this.errorService.errorPath(error.status);
      }
    );
    this.commentStr = "";
  }

  getComments() {
    this.http.get(this.hostname + "documents/" + this.documentId + "/comments", { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        this.comments = json;
        for (let c of this.comments) {
          if (c.read) {
            c['alreadyRead'] = 'read'
          } else if (this.employeeNumber == this.authService.userdetail.employeeNumber) {
            c['alreadyRead'] = 'unreadMe'
          } else {
            c['alreadyRead'] = 'unread'
          }
        }
      },
      error => {
        this.errorService.errorPath(error.status);
      }
    )
  }

  favorite() {
    this.http.post(this.hostname + "documents/bookmark/" + this.documentId, {}, { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => { },
      error => {
        this.errorService.errorPath(error.status);
      }
    )
  }

  readComment(commentId: any) {
    this.http.post(this.hostname + "documents/" + this.documentId + "/comments/" + commentId + "/read", {}, { withCredentials: true, headers: this.authService.headerAddToken(), responseType: 'text'}).subscribe(
      commentId => {
        console.log(commentId);
        for (let c of this.comments) {
          if(c.commentId == commentId){
            c.read = true;
            c['alreadyRead'] = 'read';
          }
        }
      },
      error => {
        this.errorService.errorPath(error.status);
      }
    )
  }

  debug(event): void {
    console.log(event);
  }
}
