import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit, Inject } from '@angular/core';

import { HttpService } from '../services/http.service';
import { AuthService } from '../services/auth.service';
import { ErrorService } from '../services/error.service';
import { ConvertDateService } from '../services/convert-date.service';
import 'rxjs/add/operator/map';

import { Block } from '../model/Block';
import { Comment } from '../model/Comment';
import { Template } from '../model/Template';
import { Document } from '../model/Document';
import { TypeConversionService } from '../services/type-conversion.service';
import { validateConfig } from '@angular/router/src/config';
import { ValidatorService } from '../services/validator.service';


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
  private blocks: Map<string, Block> = new Map();

  constructor(
    public convert: ConvertDateService,
    private http: HttpService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private errorService: ErrorService,
    private convertService: TypeConversionService,
    private varidator: ValidatorService,
  ) {
    this.authService.getUserDetail();
  }

  ngOnInit() {
    this.load(this.route.snapshot.paramMap.get('id'));
  }

  load(documentId: string): void {
    this.http.getWithPromise('templates/blocks').then(res => {
      this.blocks = this.convertService.makeTemplateBlockMap(this.convertService.makeTemplateBlockList(res));
      return this.http.getWithPromise('documents/' + documentId);
    }, error => {
      this.errorService.errorPath(error.status);
    }).then(res => {
      this.document = this.convertService.makeDocument(res);
      return this.http.getWithPromise('templates/' + this.document.templateId);
    }, error => {
      this.errorService.errorPath(error.status);
    }).then(res => {
      this.template = this.convertService.makeTemplate(res, this.blocks, this.document.values.map(ary => ary.length));
      return this.http.getWithPromise('templates/' + this.document.templateId + '/tags');
    }, error => {
      this.errorService.errorPath(error.status);
    }).then(res => {
      this.tags = this.tags.concat(this.convertService.makeTagNameList(res));
      return this.http.getWithPromise('documents/' + this.document.documentId + '/tags');
    }, error => {
      this.errorService.errorPath(error.status);
    }).then(res => {
      this.tags = this.tags.concat(this.convertService.makeTagNameList(res));
      return this.http.getWithPromise('documents/' + this.document.documentId + '/tags/system');
    }, error => {
      this.errorService.errorPath(error.status);
    }).then(res => {
      this.tags = this.tags.concat(this.convertService.makeTagNameList(res));
      return this.http.getWithPromise('documents/' + this.document.documentId + '/comments');
    }, error => {
      this.errorService.errorPath(error.status);
    }).then(res => {
      for (let data of res) {
        let comment = new Comment(data);
        comment.setAlreadyRead(this.document.employeeNumber, this.authService.userdetail.employeeNumber);
        this.comments.push(comment);
      }
    }, error => {
      this.errorService.errorPath(error.status);
    });
  }

  isMyDocument(): boolean {
    return this.authService.userdetail.employeeNumber == this.document.employeeNumber;
  }

  goEdit(): void {
    this.router.navigate(['/edit/' + this.document.documentId]);
  }

  isChecked(contentIdx: number, valueIdx: number): boolean {
    return this.document.values[contentIdx][valueIdx] == 'true';
  }

  submit(): void {
    if (!this.varidator.empty([this.commentStr])) {
      this.http.putWithPromise('documents/' + this.document.documentId + '/comments/create', { value: this.commentStr }).then(
        res => {
          const comment = new Comment(JSON.parse(res));
          comment.setAlreadyRead(this.document.employeeNumber, this.authService.userdetail.employeeNumber);
          this.comments.push(comment);
        }, error => {
          this.errorService.errorPath(error.status);
        }
      );
      this.commentStr = '';
    }
  }

  favorite() {
    this.document.isFav = !this.document.isFav;
    this.http.post('documents/bookmark/' + this.document.documentId, { selected: this.document.isFav }).subscribe(
      success => {
      }, error => {
        this.errorService.errorPath(error.status);
      }
    )
  }

  readComment(idx: number) {
    let data = {
      employeeNumber: this.comments[idx].employeeNumber,
    }
    console.log(data);
    this.http.postWithPromise('documents/' + this.document.documentId + '/comments/' + this.comments[idx].commentId + '/read', data).then(
      success => {
        this.comments[idx].updateToRead();
      }, error => {
        this.errorService.errorPath(error.status);
      }
    )
  }

  commenterIcon(idx: number): string {
    return this.http.getHostname() + "icon/" + this.comments[idx].employeeNumber;
  }

  eventHandler(event) {
    if (event.keyCode == 10) {
      this.submit();
    }
  }
}
