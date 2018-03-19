import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { HttpService } from '../services/http.service';
import { AuthService } from '../services/auth.service';
import { ConvertDateService } from '../services/convert-date.service';
import { ErrorService } from '../services/error.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import { Block } from '../model/Block';
import { Template } from '../model/Template';
import { Document } from '../model/Document';
import { Comment } from '../model/Comment';


@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {

  public template: Template = new Template();
  public document: Document = new Document();
  public tags: string[] = new Array();
  public comments: Comment[] = new Array();
  public commentStr: string;
  private blocks: { [key: string]: Block } = {};

  constructor(
    public convert: ConvertDateService,
    private http: HttpService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private errorService: ErrorService,
  ) {
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.document.documentId = this.route.snapshot.paramMap.get('id');
    this.load();
    this.getComments();
  }

  load(): void {
    this.http.get('templates/blocks').subscribe(res => {
      this.setTemplateBlocks(res);
    }, error => {
      this.errorService.errorPath(error.status);
    });

    this.http.getWithPromise('documents/' + this.document.documentId).then(res => {
      this.setDocument(res);
      return this.http.getWithPromise('templates/' + this.document.templateId);
    }, error => {
      this.errorService.errorPath(error.status);
    }).then(res => {
      this.setTemplate(res);
      return this.http.getWithPromise('templates/' + this.document.templateId + '/tags');
    }, error => {
      this.errorService.errorPath(error.status);
    }).then(res => {
      this.setTemplateTags(res);
      return this.http.getWithPromise('documents/' + this.document.documentId + '/tags');
    }, error => {
      this.errorService.errorPath(error.status);
    }).then(res => {
      this.setDocumentTags(res);
      return this.http.getWithPromise('documents/' + this.document.documentId + '/tags/system');
    }, error => {
      this.errorService.errorPath(error.status);
    }).then(res => {
      this.setDocumentSystemTags(res);
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  getComments(): void {
    this.http.get('documents/' + this.document.documentId + '/comments').subscribe(
      res => {
        for (let data of res) {
          let comment = new Comment(data);
          comment.setAlreadyRead(this.document.employeeNumber, this.authService.userdetail.employeeNumber);
          this.comments.push(comment);
        }
        console.log(this.comments);
      },
      error => {
        this.errorService.errorPath(error.status);
      }
    )
  }

  isMyDocument(): boolean {
    return this.authService.userdetail.employeeNumber == this.document.employeeNumber;
  }

  goEdit(): void {
    this.router.navigate(['/edit/' + this.document.documentId]);
  }

  setTemplateBlocks(blocks: Object): void {
    for (let idx in blocks) {
      let block = new Block(blocks[idx]);
      this.blocks[block.blockId] = block;
    }
  }

  setTemplate(data: Object): void {
    this.template.setTemplateInfo(data);
    let sizeList = this.document.values.map(ary => ary.length);
    this.template.setContents(data['contents'], this.blocks, sizeList);
  }

  setDocument(data: Object): void {
    this.document.setDocumentInfo(data);
    this.document.setValues(data['contents']);
  }

  setTemplateTags(tags: Object): void {
    for (let t of JSON.parse(JSON.stringify(tags))) {
      this.tags.push(t.tagName);
    }
  }

  setDocumentTags(tags: Object): void {
    for (let t of JSON.parse(JSON.stringify(tags))) {
      this.tags.push(t.tagName);
    }
  }

  setDocumentSystemTags(tags: Object): void {
    for (let t of JSON.parse(JSON.stringify(tags))) {
      this.tags.push(t.tagName);
    }
  }

  isChecked(contentIdx: number, valueIdx: number): boolean {
    return this.document.values[contentIdx][valueIdx] == 'true';
  }

  submit(): void {
    var postComment = {
      value: this.commentStr
    }
    this.http.put('documents/' + this.document.documentId + '/comments/create', postComment).subscribe(
      res => {
        const comment = new Comment(res);
        comment.setAlreadyRead(this.document.employeeNumber, this.authService.userdetail.employeeNumber);
        this.comments.push(comment);
      },
      error => {
        this.errorService.errorPath(error.status);
      }
    );
    this.commentStr = '';
  }

  favorite() {
    this.http.post('documents/bookmark/' + this.document.documentId, { selected: this.document.isFav }).subscribe(
      syccess => {
      },
      error => {
        this.errorService.errorPath(error.status);
      }
    )
  }

  readComment(idx: number) {
    this.http.post('documents/' + this.document.documentId + '/comments/' + this.comments[idx].commentId + '/read', {}).subscribe(
      success => {
        this.comments[idx].updateToRead();
      },
      error => {
        this.errorService.errorPath(error.status);
      }
    )
  }
}
