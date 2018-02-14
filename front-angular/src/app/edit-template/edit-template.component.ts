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
        let c = JSON.parse(JSON.stringify(this.contentBases[id]));
        let newid = 'comp-' + Math.floor( Math.random() * 1000000);
        c.id = newid;
        this.contents.push(c);
        this.values[newid] = new Array();
      }
    });
  }

  ngOnInit() {
    this.templateId = this.route.snapshot.paramMap.get('id');

    this.http.get(this.hostname + 'templates/blocks').subscribe(
      json => {
        this.blocks = json;

        for(var b of this.blocks) {
          if(b.additionalType == "template"){
            b.items.push(b.addItem);
          }
          this.contentBases[b.blockId] = b;
        }
      },
      error => {
        this.blocks = error;
      }
    );
  }

  addItem(e: any): void {
    let target = e.path[4].id;
    for(let content of this.contents){
      if(content.id == target){
        let item = this.contentBases[content.blockId].addItem;
        content.items.push(item);
      }
    }
  }

  removeItem(e: any): void {
    let target = e.path[4].id;
    for(let content of this.contents) {
      if(content.id == target) {
        let len = content.items.length;
        let min = this.contentBases[content.blockId].items.length;
        console.log(len + " " + min);
        if(len > min){
          content.items.pop();
        }
      }
    }
  }

  removeBlock(e: any): void {
    let target= e.path[2].id;
    for(let idx in this.contents) {
      if(this.contents[idx].id == target){
        this.contents.splice(+idx, 1);
      }
    }
  }

  trackByIndex(index: number, value: number) {
    return index;
  }

  submit(type): void {
    for(let content of this.contents){
      console.log("content: " + content.blockName + " size: " + this.values[content.id].length);
      // for(let i in this.values[content.id]){
        console.log(this.values[content.id]);
      
    }
  }

}
