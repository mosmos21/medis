import { Block } from "./Block";

export class Document {

  public documentId: string;
  public documentName: string;
  public templateId: string;
  public employeeNumber: string;
  public authorName: string;
  public documentCreateDate: number;
  public isFav: boolean;
  public values: string[][] = new Array();

  constructor() { }

  public setDocumentInfo(data: Object): void {
    this.documentId = data['documentId'];
    this.templateId = data['templateId'];
    this.documentName = data['documentName'];
    this.employeeNumber = data['employeeNumber'];
    this.documentCreateDate = data['documentCreateDate'];
    this.authorName = data['name'];
    this.isFav = data['selected'];
  }

  initValues(blocks: Block[]): void {
    this.values = new Array(blocks.length);
    for (let i = 0; i < blocks.length; i++) {
      this.values[i] = new Array();
      for (let item of blocks[i].items) {
        switch (item.documentType) {
          case 'text': case 'textarea':
            this.values[i].push('');
            break;
          case 'radio': case 'checkbox':
            this.values[i].push('false');
            break;
        }
      }
    }
  }

  public setValues(contents: any[]): void {
    this.values = new Array(contents.length);
    for (let idx in contents) {
      this.values[idx] = new Array();
      for (let item of contents[idx]['items']) {
        this.values[idx].push(item);
      }
    }
  }

  public toJson(type: string): any {
    let data = {
      templateId: this.templateId,
      documentName: this.documentName,
      publish: type == 'save',
    }
    if (this.documentId != 'new') {
      data['documentId'] = this.documentId;
    }

    let contents: any[] = new Array();
    for (let i = 0; i < this.values.length; i++) {
      let content = {
        contentOrder: i + 1,
      };
      let items: string[] = new Array();
      for (let value of this.values[i]) {
        items.push(value);
      }
      content['items'] = items;
      contents.push(content);
    }
    data['contents'] = contents;
    return data;
  }
}