import { Block } from './Block';

export class Template {

  public templateId: string;
  public templateName: string = '';
  public publish: boolean = false;
  public contents: TemplateContent[] = new Array();

  constructor() { }

  public setTemplateInfo(data: Object) {
    this.templateId = data['templateId'];
    this.templateName = data['templateName'];
    this.publish = data['publish'];
  }

  public setContents(data: Object, blocks: Map<string, Block>, documentValuesSize?: number[]): void {
    for (let i in data) {
      let values: string[] = new Array();
      for (let value of data[i]['items']) {
        values.push(value);
      }

      let block = blocks[data[i]['blockId']].clone();
      while (block.getTemplateWriteableSize() < values.length) {
        block.addItem();
      }
      if (documentValuesSize != null) {
        while (block.getDocumentWriteableSize() < documentValuesSize[i]) {
          block.addItem();
        }
      }

      this.contents.push({
        block: block,
        values: values
      });
    }
  }

  public addContent(block: Block): void {
    this.contents.push({
      block: block,
      values: new Array<string>()
    });
  }

  public toJson(type: string): any {
    let data = {
      publish: type == 'save',
      templateName: this.templateName
    }
    if (this.templateId != 'new') {
      data['templateId'] = this.templateId;
    }

    var contents: any[] = new Array();
    for (let i = 0; i < this.contents.length; i++) {
      var content = {
        contentOrder: i + 1,
        blockId: this.contents[i].block.blockId
      };
      var items: string[] = new Array();
      for (let s of this.contents[i].values) {
        items.push(s);
      }
      content['items'] = items;
      contents.push(content);
    }
    data['contents'] = contents;
    return data;
  }
}

interface TemplateContent {
  block: Block;
  values: string[];
}