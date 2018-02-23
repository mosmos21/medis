import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DragulaService } from 'ng2-dragula';
import { MatDialog } from '@angular/material';
import { NavigationService } from '../services/navigation.service';

import { MessageModalComponent } from '../message-modal/message-modal.component'

@Component({
  selector: 'app-edit-template',
  templateUrl: './edit-template.component.html',
  styleUrls: ['./edit-template.component.css']
})
export class EditTemplateComponent implements OnInit {

  public blocks: any;
  private contentBases: { [key: string]: any } = {};

  private templateId: string = "";
  private templateName: string = "";
  public contents: any[] = [];
  public values: { [key: string]: string[] } = {};

  private showBlocks: boolean;
  private showTags: boolean;

  private selected_tags = [];
  private temp_tags = [];
  private search_word = "";

  private message = "";

  // タグ一覧
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
    public dialog: MatDialog,
    private nav: NavigationService,
  ) {
    this.nav.showAdminMenu();
  }

  ngOnInit() {
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

    this.templateId = this.route.snapshot.paramMap.get('id');

    this.http.get(this.hostname + 'templates/blocks').subscribe(
      json => {
        this.blocks = json;

        for (var b of this.blocks) {
          if (b.additionalType != null) {
            for (let add of b.addItems) {
              b.items.push(add);
            }
          }
          this.contentBases[b.blockId] = b;
        }

        if (this.templateId != 'new') {
          this.assembleTemplate();
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
          this.temp_tags.push(this.tags[i]);
        }
      },
      error => {
        this.tags = error;
      }
    );

  }

  assembleTemplate(): void {
    var data;
    this.http.get(this.hostname + 'templates/' + this.templateId).subscribe(
      json => {
        data = json;
        console.log(data);
        this.templateName = data.templateName;
        for (let con of data.contents) {
          var id = this.addBlock(con.blockId);
          for (var i = 0; i < con.items.length - this.contentBases[con.blockId].items.length; i++) {
            this.addItem(id);
          }
          this.values[id] = new Array();
          for (let i of con.items) {
            this.values[id].push(i);
          }
        }
      },
      error => {
        console.log("情報の取得に失敗しました。");
      }
    );
  }

  trackByIndex(index: number, value: number) {
    return index;
  }

  clickRemoveBlock(e: any): void {
    this.removeBlock(e.path[2].id);
  }

  clickAddItem(e: any): void {
    this.addItem(e.path[4].id);
  }

  clickRemoveItem(e: any): void {
    this.removeItem(e.path[4].id);
  }

  addBlock(id: string): string {
    let c = JSON.parse(JSON.stringify(this.contentBases[id]));
    let newid = 'comp-' + Math.floor(Math.random() * 1000000);
    c.id = newid;
    this.contents.push(c);
    this.values[newid] = new Array();
    return newid;
  }

  removeBlock(target: string): void {
    for (let idx in this.contents) {
      if (this.contents[idx].id == target) {
        this.contents.splice(+idx, 1);
      }
    }
  }

  addItem(target: string): void {
    console.log(target)
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
            this.values[target].pop();
          }
        }
      }
    }
  }

  data2Json(type: string): any {
    var data = {
      publish: type == "save",
      templateName: this.templateName
    }
    if(this.templateId != "new") {
      data["templateId"] = this.templateId;
    }

    var contents: any[] = [];
    for (let i = 0; i < this.contents.length; i++) {
      var content = {
        contentOrder: i + 1,
        blockId: this.contents[i].blockId
      };
      var items: string[] = [];
      for (let s of this.values[this.contents[i].id]) {
        items.push(s);
      }
      content["items"] = items;
      contents.push(content);
    }
    data["contents"] = contents;
    console.log(data);
    return data;
  }

  submit(type): void {
    let dataJson = this.data2Json(type);

    if(type == "save") {
      this.message = "テンプレートを保存し、公開しました。"
    } else {
      this.message = "テンプレートを下書きとして保存しました。"
    }

    if(this.templateId == 'new'){
      this.http.put(this.hostname + "templates/new", dataJson).subscribe(
        json => {
          let dialogRef = this.dialog.open(MessageModalComponent, {
            data: {
              message: this.message
            }
          });
        },
        error => {
          // TODO
        }
      );
    }else{
      this.http.post(this.hostname + "templates/" + this.templateId, dataJson).subscribe(
        json => {
          let dialogRef = this.dialog.open(MessageModalComponent, {
            data: {
              message: this.message
            }
          });
        },
        error => {
          // TODO
        }
      );
    }
  }

  addTags(event: any) {
    if (this.selected_tags.length < 4) {
      var str = event.path[0].innerText;
      this.selected_tags.push(str);
      var i = this.temp_tags.length;
      console.log(str);
      while (i--) {
        if (this.temp_tags[i]["tagName"] == str) {
          this.temp_tags.splice(i, 1);
        }
      }
    }
    this.search_word = "";
  }

  deleteTags(event: any) {
    var str = event.path[0].innerText;
    var i = this.tags.length;
    var bool = false;
    while(i--) {
      if(this.tags[i]["tagName"] == str) {
        this.temp_tags.push(this.tags[i]);
      }
    }
    var idx = this.selected_tags.indexOf(str);
    console.log(str);
    console.log(idx);
    if (idx >= 0) {
      this.selected_tags.splice(idx, 1);
    }
  }

  searchTag() {
    var i = this.temp_tags.length;
    var j = this.selected_tags.length;
    var str = this.search_word;
    var bool = true;
    var target_tags: any = [];
    while (i--) {
      if(this.temp_tags[i]["tagName"] == str) {
        bool = false;
      }
      if(this.temp_tags[i]["tagName"].indexOf(str) > -1) {
        target_tags.push(this.temp_tags[i]);
      }
    }
    while (j--) {
      if(str == this.selected_tags[j]) {
        bool = false;
      }
    }
    /*
    if(str != "" && bool) {
      target_tags.unshift({
        tagId: "",
        tagName: str,
      });
    }
    */
    return target_tags;
  }
}
