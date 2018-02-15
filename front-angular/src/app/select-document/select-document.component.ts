import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-select-document',
  templateUrl: './select-document.component.html',
  styleUrls: ['./select-document.component.css']
})
export class SelectDocumentComponent implements OnInit {

  private title: string = "テンプレート選択";

  constructor() { }

  ngOnInit() {
  }

}
