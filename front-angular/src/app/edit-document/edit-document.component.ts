import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { DragulaService } from 'ng2-dragula';

@Component({
  selector: 'app-edit-document',
  templateUrl: './edit-document.component.html',
  styleUrls: ['./edit-document.component.css']
})
export class EditDocumentComponent implements OnInit {

  private templateId: string = "";
  private documentId: string = "";
  private documentName: string = "";

  public blocks: any;
  private contentBases: { [key: string]: any } = {};

  public contents: any[] = [];
  public templateValues: { [key: string]: string[] } = {};
  public documentValues: { [key: string]: string[] } = {};

  private fixedtags: string[] = ["2018年度新人研修"];
  private selected_tags = [];
  private temp_tags = [];
  private seach_word = "";
  private tags: any = [
    {
      tagId: "",
      tagName: ""
    }
  ];

  constructor(
    @Inject('hostname') private hostname: string,
    private http: HttpClient,
    private route: ActivatedRoute,
    private dragulaService: DragulaService,
  ) {
    dragulaService.setOptions("template-block", {
      copy: function (el: any, source: any) {
        return source.id === "template_block_list";
      },
      accepts: function (el: any, source: any) {
        return source.id !== "template_block_list";
      }
    });

    dragulaService.drop.subscribe((value) => {
      let [e, el] = value.slice(1);
      let id = e.childNodes[1].id;
      if (id != '') {
        e.remove();
        this.addBlock(id);
      }
    });
  }

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

    this.http.get(this.hostname + 'tags').subscribe(
      json => {
        this.tags = json;
        var i = this.tags.length;
        while (i--) {
          this.temp_tags.push(this.tags[i]["tagName"]);
        }
      },
      error => {
        this.tags = error;
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
        if (callback != null) {
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
      },
      error => {
        console.log("情報の取得に失敗しました");
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
    for (let c of this.contents) {
      if (c.id == id) {
        this.documentValues[id][line] = e.target.checked ? "true" : "false";
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
    console.log(dataJson);
    if(this.documentId == 'new'){
      this.http.put(this.hostname + "documents/new", dataJson).subscribe(
        json => {

        },
        error => {
          // TODO
        }
      );
    }else{
      this.http.post(this.hostname + "documents/" + this.documentId, dataJson).subscribe(
        json => {
        },
        error => {
          // TODO
        }
      );
    }
  }

  //タグ系
  addTags(event: any) {
    if (this.selected_tags.length + this.fixedtags.length < 8) {
      var str = event.path[0].innerText;
      this.selected_tags.push(str);
      var i = this.temp_tags.length;
      while (i--) {
        if (this.temp_tags[i] == str) {
          this.temp_tags.splice(i, 1);
        }
      }
    }
    this.seach_word = "";
  }

  deleteTags(event: any) {
    var str = event.path[0].innerText;
    this.temp_tags.push(str);
    var idx = this.selected_tags.indexOf(str);
    if (idx >= 0) {
      this.selected_tags.splice(idx, 1);
    }
  }

  searchTag() {
    var i = this.temp_tags.length;
    var str = this.seach_word;
    var target_tags: any = [];
    while (i--) {
      if (this.temp_tags[i].indexOf(str) > -1) {
        var temp_str = this.temp_tags[i];
        target_tags.push(temp_str);
      }
    }
    return target_tags;
  }
}
