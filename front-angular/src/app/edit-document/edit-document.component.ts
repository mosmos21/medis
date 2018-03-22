import { Location } from '@angular/common';
import { MatDialog } from '@angular/material'
import { DragulaService } from 'ng2-dragula';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit, Inject, HostListener } from '@angular/core';

import { MessageModalComponent } from '../message-modal/message-modal.component'

import { AuthService } from '../services/auth.service';
import { ErrorService } from '../services/error.service';
import { SearchService } from '../services/search.service';
import { ValidatorService } from '../services/validator.service';
import { NavigationService } from '../services/navigation.service';
import { MsgToSidenavService } from '../services/msg-to-sidenav.service';
import { TypeConversionService } from '../services/type-conversion.service';

import { TagContent } from '../model/Tag';
import { Block } from '../model/Block';
import { Document } from '../model/Document';
import { Template } from '../model/Template';
import { HttpService } from '../services/http.service';

@Component({
  selector: 'app-edit-document',
  templateUrl: './edit-document.component.html',
  styleUrls: ['./edit-document.component.css']
})
export class EditDocumentComponent implements OnInit {

  public blocks: Map<string, Block> = new Map<string, Block>();
  public blockList: [string, string][] = new Array();
  public template: Template = new Template();
  public document: Document = new Document();
  public fixedTags: TagContent[] = new Array();
  private message: string = '';

  constructor(
    public dialog: MatDialog,
    public searchService: SearchService,
    private nav: NavigationService,
    private http: HttpService,
    private valid: ValidatorService,
    private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private authService: AuthService,
    private errorService: ErrorService,
    private dragulaService: DragulaService,
    private convertService: TypeConversionService,
    private msgToSidenavService: MsgToSidenavService,
  ) {
    this.nav.show();
    this.authService.getUserDetail();
    this.searchService.init();
  }

  ngOnInit() {
    this.load(this.route.snapshot.paramMap.get('id'));
    this.searchService.getTags();
    let test = new Array(4);
  }

  load(documentId: string): void {
    this.http.getWithPromise('templates/blocks').then(res => {
      this.blocks = this.convertService.makeTemplateBlockMap(this.convertService.makeTemplateBlockList(res));
      for (let id in this.blocks) {
        this.blockList.push([id, this.blocks[id].blockName]);
      }
    }, error => {
      this.errorService.errorPath(error.status);
    }).then(() => {
      if (documentId != 'new') {
        this.http.getWithPromise('documents/' + documentId).then(res => {
          this.document = this.convertService.makeDocument(res);
          return this.http.getWithPromise('templates/' + this.document.templateId);
        }, error => {
          this.errorService.errorPath(error.status);
        }).then(res => {
          this.template = this.convertService.makeTemplate(res, this.blocks, this.document.values.map(ary => ary.length));
          this.loadTags();
        }, error => {
          this.errorService.errorPath(error.status);
        });
      } else {
        this.http.get('templates/' + this.route.snapshot.fragment).subscribe(res => {
          this.template = this.convertService.makeTemplate(res, this.blocks);
          this.document.templateId = this.template.templateId;
          this.document.initValues(this.template.contents.map(content => content.block));
          this.loadTags();
        }, error => {
          this.errorService.errorPath(error.status);
        });
      }
    });
  }

  loadTags(): void {
    if (this.template.templateId != 'null') {
      this.http.get('templates/' + this.template.templateId + '/tags').subscribe(res => {
        this.fixedTags = this.convertService.makeTagList(res);
        this.searchService.excludeTagList(this.fixedTags);
      }, error => {
        this.errorService.errorPath(error.status);
      });
    }
    if (this.document.documentId != null && this.document.documentId != 'new') {
      this.http.get('documents/' + this.document.documentId + '/tags').subscribe(res => {
        this.searchService.selectedTags = this.convertService.makeTagList(res);
        this.searchService.excludeTagList(this.searchService.selectedTags);
      }, error => {
        this.errorService.errorPath(error.status);
      });
    }
  }

  isChecked(contentIdx: number, valueIdx: number): boolean {

    return this.document.values[contentIdx][valueIdx] == 'true';
  }

  clickRadio(contentIdx: number, valueIdx: number): void {
    const size = this.template.contents[contentIdx].block.getDocumentWriteableSize();
    for (let i = 0; i < size; i++) {
      this.document.values[contentIdx][i] = String(i == valueIdx);
    }
  }

  clickCheckBox(contentIdx: number, valueIdx: number): void {
    console.log(contentIdx + ' ' + valueIdx);
    this.document.values[contentIdx][valueIdx] = String(this.document.values[contentIdx][valueIdx] == 'false');
  }

  addBlock(blockId: string): void {
    this.template.addContent(this.blocks[blockId].clone());
  }

  addItem(idx: number): void {
    this.template.contents[idx].block.addItem();
  }

  removeItem(idx: number): void {
    this.template.contents[idx].block.removeItem();
  }

  submit(type: string): void {
    if (this.valid.empty([this.document.documentName])) {
      this.message = 'ドキュメント名を入力してください。';
      let dialogRef = this.dialog.open(MessageModalComponent, { data: { message: this.message } });
    } else {
      let data = this.document.toJson(type);
      console.log(data);
      if (type == 'save') {
        this.message = 'ドキュメントを保存し、公開しました。';
      } else {
        this.message = 'ドキュメントを下書きとして保存しました。';
      }

      if (this.document.documentId == null) {
        this.http.put('documents/new', data).subscribe(id => {
          this.document.documentId = id;
          this.submitTags();
        }, error => {
          this.errorService.errorPath(error.status);
        });
      } else {
        this.http.post('documents/' + this.document.documentId, data).subscribe(success => {
          this.submitTags();
        }, error => {
          this.errorService.errorPath(error.status);
        });
      }
      let dialogRef = this.dialog.open(MessageModalComponent, { data: { message: this.message } });
      dialogRef.afterClosed().subscribe(result => {
        if (type == 'save') {
          this.msgToSidenavService.sendMsg();
          this.router.navigate(['browsing/' + this.document.documentId]);
        } else {
          this.msgToSidenavService.sendMsg();
          this.router.navigate(['edit']);
        }
      });
    }
  }

  goBack(): void {
    this.location.back();
  }

  submitTags(): void {
    let tags = this.searchService.selectedTags.concat(this.searchService.newTags);
    this.http.put('documents/' + this.document.documentId + '/tags', tags).subscribe(success => {
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  @HostListener('window:beforeunload', ['$event'])
  beforeUnload(e: Event): void {
    e.returnValue = true;
  }
}
