import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NavigationService } from '../services/navigation.service';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  private errorNum;
  public errorNumUrl;
  public message;

  constructor(
    route: ActivatedRoute,
    nav: NavigationService,
  ) {
    nav.hide();
    this.errorNum = route.snapshot.params['id'];
    if (this.errorNum == '400') {
      this.message = '予期せぬリクエストがありました。'
    } else if (this.errorNum == '403') {
      this.message = 'コンテンツへのアクセス権がありませんでした。'
    } else if (this.errorNum == '500') {
      this.message = '技術的な問題が発生しました。'
    } else if (this.errorNum == '404' || this.errorNum == null) {
      this.errorNum = '404'
      this.message = 'ページもしくはデータが見つかりませんでした。'
    } else if (this.errorNum == '1') {
      this.errorNum = '0'
      this.message = 'ドキュメントの編集権限がありません。'
    } else if (this.errorNum == '2') {
      this.errorNum = '0'
      this.message = '閲覧権限のないページです。'
    } else {
      this.errorNum = '0'
      this.message = '何かしらの問題がありました。'
    }
    this.errorNumUrl = '../../assets/image/verySorry' + this.errorNum + '.png'
  }

  ngOnInit() {
  }

}
