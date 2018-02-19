import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edit-document',
  templateUrl: './edit-document.component.html',
  styleUrls: ['./edit-document.component.css']
})
export class EditDocumentComponent implements OnInit {

  private templateId: string = "";
  private documentId: string = "";

  public blocks: any;
  private contentBases: { [key: string]: any } = {};

  public contents: any[] = [];
  public templateValues: { [key: string]: string[] } = {};
  public documentValues: { [key: string]: string[] } = {};

  private fixedtags: string[] = ["2018年度新人研修"];
  private tags: string[] = ["プログラミング研修"];

  constructor(
    @Inject('hostname') private hostname: string,
    private http: HttpClient,
    private route: ActivatedRoute,
  ) {}

  ngOnInit() {
    this.documentId = this.route.snapshot.paramMap.get('id');

    this.http.get(this.hostname + 'templates/blocks').subscribe(
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
        this.blocks = error;
      }
    );
  }

  assembleTemplate(templateId: string, callback: any): void {
    this.templateId = templateId;

    var data;
    this.http.get(this.hostname + 'templates/' + templateId).subscribe(
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
        if(callback != null) {
      callback();
    }
      },
      error => {
        console.log("情報の取得に失敗しました。");
      }
    );
    
  }

  assembleDocument() {
    var data;
    this.http.get(this.hostname + 'documents/' + this.documentId).subscribe(
      json => {
        data = json;
        this.assembleTemplate(data.templateId, () => {
          for(let i = 0; i < data.contents.length; i++){
            let id = this.contents[i].id;
            for(let a of data.contents[i].items){
              this.documentValues[id].push(a);
            }
            while(this.contents[i].items.length < this.documentValues[id].length + this.contents[i].offset){
              this.addItem(id);
            }
          }
        });
      },
      error => {
        console.log("情報の取得に失敗しました");
      }
    );
  }

  isChecked(id: string, line: number): boolean {
    return this.documentValues[id][line] == "true";
  }

  clickDeleteTag(e: any): void {

  }

  clickAddItem(e: any): void {
    this.addItem(e.path[4].id);
  }

  clickRemoveItem(e: any): void {
    this.removeItem(e.path[4].id);
  }

  clickRadio(id: string, line: number){
    for(let c of this.contents){
      if(c.id == id) {
        for(let i = 0; i < c.items.length - c.offset; i++){
          this.documentValues[id][i] = (i == line ? "true": "false");
        }
      }
    }
  }

  clickCheckBox(id: string, line: number, e: any){
    for(let c of this.contents){
      if(c.id == id) {
        this.documentValues[id][line] = e.target.checked ? "true" : "false";
      }
    }
  }

  deleteTag(target: string): void {

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
          while(content.items.length - content.offset < this.documentValues[target].length){
            this.documentValues[target].pop();
          }
        }
      }
    }
  }

  data2Json(type: string): any {
    var data = {
      templateId: this.templateId,
      isPublish: type == "save"
    }
    if(this.documentId.length > 0){
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
    let data = this.data2Json(type);
    console.log(data);
  }
}
