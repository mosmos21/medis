import { Block } from "./Block";

export class Template {

  private defaultValue: string = '[Not Found]';

  public templateId: string;
  public templateName: string;
  public publish: boolean;
  public contents: TemplateContent[] = new Array();

  constructor() { }

  public setTemplateInfo(data: Object) {
    this.templateId = data['templateId'];
    this.templateName = data['templateName'];
    this.publish = data['publish'];
  }

  public setContents(data: Object, blocks: { [key: string]: Block }, documentValuesSize: number[]): void {
    for (let i in data) {
      let values: string[] = new Array();
      for (let value of data[i]["items"]) {
        values.push(value);
      }

      let block = blocks[data[i]['blockId']].clone();
      while (block.getDocumentWriteableSize() < documentValuesSize[i]) {
        block.addItem();
      }

      this.contents.push({
        block: block,
        contentOrder: data[i]['contentOrder'],
        values: values
      });
    }
  }
}

interface TemplateContent {
  block: Block;
  contentOrder: number;
  values: string[];
}