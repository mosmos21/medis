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

  private blocks: any;
  private contentBases: {[key: string]: any} = {};

  private templateId: string;
  private contents: any[] = [];

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
      var id = e.childNodes[1].id;
      console.log(id);
      if(id != ''){
        e.remove();
        this.contents.push(this.contentBases[id]);
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

  addContents(): void {

  }

}
