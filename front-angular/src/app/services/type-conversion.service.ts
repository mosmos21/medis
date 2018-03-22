import { Injectable } from "@angular/core";
import { Block } from "../model/Block";
import { Template } from "../model/Template";
import { Document } from "../model/Document";
import { TagContent } from "../model/Tag";

@Injectable()
export class TypeConversionService {

  makeTemplateBlockList(data: Object): Block[] {
    let blocks: Block[] = new Array();
    for (let idx in data) {
      let block = new Block(data[idx]);
      blocks.push(block);
    }
    return blocks;
  }

  makeTemplateBlockMap(blocks: Block[]): Map<string, Block> {
    let blockMap: Map<string, Block> = new Map();
    blocks.forEach(block => blockMap[block.blockId] = block);
    return blockMap;
  }

  makeTemplate(data: Object, blocks: Map<string, Block>, sizeList?: number[]): Template {
    let template: Template = new Template();
    template.setTemplateInfo(data);
    template.setContents(data['contents'], blocks, sizeList);
    return template;
  }

  makeDocument(data: Object): Document {
    let document: Document = new Document();
    document.setDocumentInfo(data);
    document.setValues(data['contents']);
    return document;
  }

  makeTagList(data: Object): TagContent[] {
    let tags: TagContent[] = new Array();
    for (let idx in data) {
      tags.push({
        tagId: data[idx]['tagId'],
        tagName: data[idx]['tagName']
      });
    }
    return tags;
  }

  makeTagNameList(data: Object): string[] {
    let tags: string[] = new Array();
    for (let idx in data) {
      tags.push(data[idx]['tagName']);
    }
    return tags;
  }
}