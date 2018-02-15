import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DragulaService } from 'ng2-dragula';

@Component({
  selector: 'app-edit-template',
  templateUrl: './edit-template.component.html',
  styleUrls: ['./edit-template.component.css']
})
export class EditTemplateComponent implements OnInit {

  public blocks: any;
  private contentBases: {[key: string]: any} = {};

  private templateId: string;
  public contents: any[] = [];
  public values: {[key: string]: string[]} = {};

  constructor(@Inject('hostname') private hostname: string, private http: HttpClient,
    private route: ActivatedRoute, private dragulaService: DragulaService) {
    dragulaService.setOptions("template-block", {
      copy: function (el: any , source: any) {
        return source.id === "template_block_list";
      },
      accepts: function(el: any , source: any) {
        return source.id !== "template_block_list";
      }
    });

    dragulaService.drop.subscribe((value) => {
      let [e, el] = value.slice(1);
      let id = e.childNodes[1].id;
      if(id != ''){
        e.remove();
        this.addBlock(id);
      }
    });
  }

  ngOnInit() {
    this.templateId = this.route.snapshot.paramMap.get('id');

    this.http.get(this.hostname + 'templates/blocks').subscribe(
      json => {
        this.blocks = json;

        for(var b of this.blocks) {
          if(b.additionalType != null){
            for(let add of b.addItems){
              b.items.push(add);
            }
          }
          this.contentBases[b.blockId] = b;
        }

        if(this.templateId != 'new'){
          this.assembleTemplate();
        }
      },
      error => {
        this.blocks = error;
      }
    );
  }

  assembleTemplate(): void {
    var data;
    this.http.get(this.hostname + 'templates/' + this.templateId).subscribe(
      json => {
        data = json;
        for(let con of data.contents){
          var id = this.addBlock(con.blockId);
          for(var i = 0; i < con.items.length - this.contentBases[con.blockId].items.length; i++){
            this.addItem(id);
          }
          this.values[id] = new Array();
          for(let i of con.items){
            this.values[id].push(i);
          }
        }
      },
      error =>{
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
    let newid = 'comp-' + Math.floor( Math.random() * 1000000);
    c.id = newid;
    this.contents.push(c);
    this.values[newid] = new Array();
    return newid;
  }

  removeBlock(target: string): void {
    for(let idx in this.contents) {
      if(this.contents[idx].id == target){
        this.contents.splice(+idx, 1);
      }
    }
  }

  addItem(target: string): void {
    for(let content of this.contents){
      if(content.id == target){
        let items = this.contentBases[content.blockId].addItems;
        for(let add of items){
          content.items.push(add);
        }
      }
    }
  }

  removeItem(target: string): void {
  for(let content of this.contents) {
      if(content.id == target) {
        let len = content.items.length;
        let min = this.contentBases[content.blockId].items.length;
        let size = this.contentBases[content.blockId].addItems.length;
        if(len > min){
          for(let i = 0; i < size; i++){
            content.items.pop();
            this.values[target].pop();
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
    var contents: any[] = [];
    for(let i = 0; i < this.contents.length; i++){
      var content = {
        contentOrder: i + 1,
        blockId: this.contents[i].blockId,
      };
      var items: string[] = [];
      for(let s of this.values[this.contents[i].id]){
        items.push(s);
      }
      content["items"] = items;
      contents.push(content);
    }
    data["contents"] = contents;
    return data;
  }

  submit(type): void {
    let data = this.data2Json(type);
    console.log(data);
    this.http.post(this.hostname + "template/" + this.templateId, data).subscribe(
     json => {
       // TODO
     },
     error => {
       // TODO
     }
    );
  }
}
