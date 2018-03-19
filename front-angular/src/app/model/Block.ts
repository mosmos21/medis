
export class Block {

  public id: string;
  public blockId: string;
  public blockName: string;
  public unique: boolean;
  public templateWrapType: string;
  public documentWrapType: string;
  public items: BlockItem[] = new Array();
  public additionalType: string;
  public addItems: BlockItem[] = new Array();
  public offset: number;

  constructor(data?: Object) {
    if (data == null) return;
    this.blockId = data['blockId'];
    this.blockName = data['blockName'];
    this.unique = data['unique'];
    this.templateWrapType = data['templateWrapType'];
    this.documentWrapType = data['documentWrapType'];
    this.items = this.getItems(data['items']);
    this.additionalType = data['additionalType'];
    this.addItems = this.getItems(data['addItems']);
    if (0 < this.addItems.length) {
      this.addItem();
    }
    this.offset = this.items.length;
  }

  public clone(): Block {
    let block = new Block();
    block.blockId = this.blockId;
    block.blockName = this.blockName;
    block.unique = this.unique;
    block.templateWrapType = this.templateWrapType;
    block.documentWrapType = this.documentWrapType;
    block.items = this.items.slice();
    block.additionalType = this.additionalType;
    block.addItems = this.addItems.slice();
    block.offset = this.offset;
    return block;
  }

  public getTemplateValueIndex(idx: number): number {
    const cnt = this.items.slice(0, idx).filter(ele => {
      return ele.templateType == 'hidden' || ele.templateType == 'span';
    }).length;
    return idx - cnt + 1;
  }

  public getDocumentValueIndex(idx: number): number {
    const cnt = this.items.slice(0, idx).filter(ele => {
      return ele.documentType == 'hidden' || ele.documentType == 'span';
    }).length;
    return idx - cnt;
  }

  public getTemplateWriteableSize(): number {
    return this.items.filter(ele => {
      return ele.templateType != 'span';
    }).length + 1;
  }

  public getDocumentWriteableSize(): number {
    return this.items.filter(ele => {
      return ele.documentType != 'hidden' && ele.documentType != 'span';
    }).length;
  }

  private getItems(ary: Object[]): BlockItem[] {
    let items = new Array();
    for (let idx in ary) {
      items.push({
        templateType: ary[idx]['templateType'],
        documentType: ary[idx]['documentType'],
        value: ary[idx]['value']
      });
    }
    return items;
  }

  public addItem(): void {
    this.items = this.items.concat(this.addItems);
  }

  public removeItem(): void {
    if (0 < this.items.length - this.offset) {
      for (let i = 0; i < this.addItems.length; i++) {
        this.items.pop();
      }
    }
  }
}

interface BlockItem {
  templateType: string;
  documentType: string;
  value: string;
}