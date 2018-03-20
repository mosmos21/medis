import { Location } from '@angular/common';
import { MatDialog } from '@angular/material';
import { Component, OnInit, Inject, HostListener } from '@angular/core';
import { ActivatedRoute, Router, NavigationStart, RouterEvent } from '@angular/router';

import { MessageModalComponent } from '../message-modal/message-modal.component'

import { AuthService } from '../services/auth.service';
import { HttpService } from '../services/http.service';
import { ErrorService } from '../services/error.service';
import { SearchService } from '../services/search.service';
import { DragulaService } from 'ng2-dragula';
import { ValidatorService } from '../services/validator.service';
import { NavigationService } from '../services/navigation.service';
import { TypeConversionService } from '../services/type-conversion.service';

import { Block } from '../model/Block';
import { Template } from '../model/Template';

@Component({
  selector: 'app-edit-template',
  templateUrl: './edit-template.component.html',
  styleUrls: ['./edit-template.component.css']
})
export class EditTemplateComponent implements OnInit {

  public blocks: Map<string, Block> = new Map<string, Block>();
  public blockList: [string, string][] = new Array();
  public template: Template = new Template();
  private message = '';

  constructor(
    public dialog: MatDialog,
    public searchService: SearchService,
    private nav: NavigationService,
    private http: HttpService,
    private route: ActivatedRoute,
    private valid: ValidatorService,
    private router: Router,
    private location: Location,
    private authService: AuthService,
    private errorService: ErrorService,
    private dragulaService: DragulaService,
    private convertService: TypeConversionService,
  ) {
    this.nav.showAdminMenu();
    this.nav.show();
    this.authService.getUserDetail();
    this.searchService.init();
    router.events.subscribe(event => {
    });
  }

  ngOnInit() {
    this.dragulaService.setOptions('template-block', {
      copy: function (el: any, source: any) {
        return source.id === 'template_block_list';
      },
      accepts: function (el: any, source: any) {
        return source.id !== 'template_block_list';
      }
    });

    this.dragulaService.drop.subscribe(value => {
      let [e, el] = value.slice(1);
      let id = e.childNodes[1].id;
      if (id != '') {
        e.remove();
        this.addBlock(id);
      }
    });
    this.load(this.route.snapshot.paramMap.get('id'));
  }

  load(templateId: string): void {
    this.http.getWithPromise('templates/blocks').then(res => {
      this.blocks = this.convertService.makeTemplateBlockMap(this.convertService.makeTemplateBlockList(res));
      for (let id in this.blocks) {
        this.blockList.push([id, this.blocks[id].blockName]);
      }
    }, error => {
      this.errorService.errorPath(error.status);
    }).then(() => {
      if (this.template.templateId != 'new') {
        this.http.getWithPromise('templates/' + templateId).then(res => {
          this.template = this.convertService.makeTemplate(res, this.blocks);
          this.template.contents
              .filter(content => content.block.additionalType == 'document')
              .forEach(content=> content.block.addItem());
          return this.http.getWithPromise('templates/' + this.template.templateId + '/tags');
        }, error => {
          this.errorService.errorPath(error.status);
        }).then(res => {
          this.searchService.selectedTags = JSON.parse(JSON.stringify(res));
        }, error => {
          this.errorService.errorPath(error.status);
        })
      }
    });
  }

  trackByIndex(index: number, value: number) {
    return index;
  }

  addBlock(blockId: string): void {
    this.template.addContent(this.blocks[blockId].clone());
  }

  removeBlock(idx: number): void {
    this.template.contents.splice(idx, 1);
  }

  addItem(idx: number): void {
    this.template.contents[idx].block.addItem();
  }

  removeItem(idx: number): void {
    this.template.contents[idx].block.removeItem();
  }

  submit(type): void {
    if (this.valid.empty(this.template.templateName)) {
      this.message = 'テンプレート名を入力してください。'
      let dialogRef = this.dialog.open(MessageModalComponent, { data: { message: this.message } });
      return;
    }
    let json = this.template.toJson(type);
    if (type == 'save') {
      this.message = 'テンプレートを保存し、公開しました。';
    } else {
      this.message = 'テンプレートを下書きとして保存しました。';
    }

    if (this.template.templateId == 'new') {
      this.http.put('templates/new', json).subscribe(
        id => {
          this.submitTags(id);
        }, error => {
          this.errorService.errorPath(error.status);
        }
      );
    } else {
      this.http.post('templates/' + this.template.templateId, json).subscribe(
        id => {
          this.submitTags(id);
        }, error => {
          this.errorService.errorPath(error.status);
        }
      );
    }

    let dialogRef = this.dialog.open(MessageModalComponent, { data: { message: this.message } });
    dialogRef.afterClosed().subscribe(result => {
      this.router.navigate(['admin/template']);
    });
  }

  submitTags(templateId: string): void {
    let tags = new Array();
    tags = this.searchService.selectedTags.concat(this.searchService.newTags);
    this.http.post('templates/' + templateId + '/tags', tags).subscribe(
      success => {
      }, error => {
        this.errorService.errorPath(error.status);
      });
  }

  goBack() {
    this.location.back();
  }

  @HostListener('window:beforeunload', ['$event'])
  beforeUnload(e: Event) {
    console.log(e);
    e.returnValue = true;
  }
}
