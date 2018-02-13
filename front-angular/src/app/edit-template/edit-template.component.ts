import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DragulaService } from 'ng2-dragula';

@Component({
  selector: 'app-edit-template',
  templateUrl: './edit-template.component.html',
  styleUrls: ['./edit-template.component.css']
})
export class EditTemplateComponent implements OnInit {

  templateId: string;

  constructor(private route: ActivatedRoute, private dragulaService: DragulaService) {
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
      e.querySelectorAll('.template_block_info')["0"].hidden = true;
      e.querySelectorAll('.template_block_body')["0"].hidden = false;
    });
  }

  ngOnInit() {
    this.templateId = this.route.snapshot.paramMap.get('id');
  }

}
