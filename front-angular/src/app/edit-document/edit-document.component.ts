import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { DragulaService } from 'ng2-dragula';
import { SearchService } from '../services/search.service';

import { MatDialog } from '@angular/material'

import { MessageModalComponent } from '../message-modal/message-modal.component'
import { ValidatorService } from '../services/validator.service';
import { AuthService } from '../services/auth.service';
import { NavigationService } from '../services/navigation.service';
import { ErrorService } from '../services/error.service';
import { MsgToSidenavService } from '../services/msg-to-sidenav.service';

@Component({
  selector: 'app-edit-document',
  templateUrl: './edit-document.component.html',
  styleUrls: ['./edit-document.component.css']
})
export class EditDocumentComponent implements OnInit {

  private message: string = "";

  private templateId: string = "";
  private documentId: string = "";
  public documentName: string = "";

  public blocks: any;
  private contentBases: { [key: string]: any } = {};

  public contents: any[] = [];
  public templateValues: { [key: string]: string[] } = {};
  public documentValues: { [key: string]: string[] } = {};

  public fixedTags: any[];

  constructor(
    @Inject('hostname') private hostname: string,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private dragulaService: DragulaService,
    private valid: ValidatorService,
    public dialog: MatDialog,
    private authService: AuthService,
    public searchService: SearchService,
    private errorService: ErrorService,
    private nav: NavigationService,
    private msgToSidenavService: MsgToSidenavService,
  ) {
    this.nav.show();
    this.authService.getUserDetail(http);
    this.searchService.init();
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

    this.dragulaService.setOptions("template-block", {
      copy: function (el: any, source: any) {
        return source.id === "template_block_list";
      },
      accepts: function (el: any, source: any) {
        return source.id !== "template_block_list";
      }
    });

    this.dragulaService.drop.subscribe((value) => {
      let [e, el] = value.slice(1);
      let id = e.childNodes[1].id;
      if (id != '') {
        e.remove();
        this.addBlock(id);
      }
    });

    this.searchService.getTags();
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
        this.getTemplateTags(templateId);
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
        this.documentName = data.documentName;
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
        this.getDocumentTags(data.documentId);
      },
      error => {
        console.log("error in assembleDocument()");
        this.errorService.errorPath(error.status);
      }
    );
  }

  getTemplateTags(templateId: string): void {
    // console.log(templateId);
    this.http.get(this.hostname + 'templates/' + templateId + "/tags", { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        this.fixedTags = JSON.parse(JSON.stringify(json));
        var i = this.fixedTags.length;
        while (i--) {
          var j = this.searchService.targetTags.length;
          while (j--) {
            if (this.fixedTags[i].tagId == this.searchService.targetTags[j].tagId) {
              this.searchService.targetTags.splice(j, 1);
              this.searchService.tempTags.splice(j, 1);
              break;
            }
          }
        }
      },
      error => {
      }
    );
  }

  getDocumentTags(documentId: string): void {
    this.http.get(this.hostname + 'documents/' + documentId + "/tags", { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
      json => {
        this.searchService.selectedTags = JSON.parse(JSON.stringify(json));
      },
      error => {
      }
    );
  }

  isChecked(id: string, line: number): boolean {
    return this.documentValues[id][line] == "true";
  }

  clickAddItem(e: any): void {
    this.addItem(e.path[5].id);
  }

  clickRemoveItem(e: any): void {
    this.removeItem(e.path[5].id);
  }

  clickRadio(id: string, line: number) {
    for (let c of this.contents) {
      if (c.id == id) {
        for (let i = 0; i < c.items.length - c.offset; i++) {
          this.documentValues[id][i] = (i == line ? "true" : "false");
        }
      }
    }
  }

  clickCheckBox(id: string, line: number, e: any) {
    //console.log(id);
    //console.log(line);
    //console.log(this.contents);
    for (let c of this.contents) {
      if (c.id == id) {
        // this.documentValues[id][line] = e.target.checked ? "true" : "false";
        this.documentValues[id][line] = (this.documentValues[id][line] == "true" ? "false" : "true");
      }
    }
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

  removeItem(target: string): void {
    for (let content of this.contents) {
      if (content.id == target) {
        let len = content.items.length;
        let min = this.contentBases[content.blockId].items.length;
        let size = this.contentBases[content.blockId].addItems.length;
        if (len > min) {
          for (let i = 0; i < size; i++) {
            content.items.pop();
          }
          while (content.items.length - content.offset < this.documentValues[target].length) {
            this.documentValues[target].pop();
          }
        }
      }
    }
  }

  data2Json(type: string): any {
    var data = {
      templateId: this.templateId,
      documentName: this.documentName,
      publish: type == "save"
    }
    if (this.documentId != "new") {
      data["documentId"] = this.documentId;
    }

    var contents: any[] = [];
    for (let i = 0; i < this.contents.length; i++) {
      var content = {
        contentOrder: i + 1,
      };
      var items: string[] = [];
      for (let s of this.documentValues[this.contents[i].id]) {
        items.push(s);
      }
      content["items"] = items;
      contents.push(content);
    }
    data["contents"] = contents;
    return data;
  }

  submit(type: string): void {
    let dataJson = this.data2Json(type);
    if (this.valid.empty(this.documentName)) {
      this.message = "ドキュメント名を入力してください。"
      let dialogRef = this.dialog.open(MessageModalComponent, {
        data: {
          message: this.message
        }
      });
    } else {
      if (type == "save") {
        this.message = "ドキュメントを保存し、公開しました。"
      } else {
        this.message = "ドキュメントを下書きとして保存しました。"
      }

      if (this.documentId == 'new') {
        this.http.put(this.hostname + "documents/new", dataJson, { withCredentials: true, headers: this.authService.headerAddToken(), responseType: 'text' }).subscribe(
          id => {
            this.documentId = id;
            this.submitTags(this.documentId);
          },
          error => {
            console.log("error in submit()");
            this.errorService.errorPath(error.status)
          }
        );
      } else {
        this.http.post(this.hostname + "documents/" + this.documentId, dataJson, { withCredentials: true, headers: this.authService.headerAddToken(), responseType: 'text' }).subscribe(
          id => {
            this.documentId = id;
            this.submitTags(this.documentId);
          },
          error => {
            console.log("error in submit()-2" + error.status);
            this.errorService.errorPath(error.status);
          }
        );
      }

      let dialogRef = this.dialog.open(MessageModalComponent, {
        data: {
          message: this.message
        }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (type == "save") {
          this.msgToSidenavService.sendMsg();
          this.router.navigate(['browsing/' + this.documentId]);
        } else {
          this.msgToSidenavService.sendMsg();
          this.router.navigate(['edit']);
        }
      });
    }
  }

  submitTags(documentId: string): void {
    let tags = new Array();
    tags = this.searchService.selectedTags.concat(this.searchService.newTags);
    this.http.put(this.hostname + "documents/" + documentId + "/tags", tags,
      { withCredentials: true, headers: this.authService.headerAddToken() }).subscribe(
        success => {
        },
        error => {
          console.log("error in submitTags()");
          this.errorService.errorPath(error.status);
        }
      );
  }

  trackByIndex(index, content) {
    return content.id;
  }
}
