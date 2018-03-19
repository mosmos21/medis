export class Document {

  private defaultValue: string = '[Not Found]';

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
    this.templateId = data["templateId"];
    this.documentName = data["documentName"];
    this.employeeNumber = data["employeeNumber"];
    this.documentCreateDate = data["documentCreateDate"];
    this.authorName = data["name"];
    this.isFav = data["selected"];
  }

  initDocumentValues(size: number): void {
    this.values = new Array(size);
  }

  public setValues(contents: any[]): void {
    this.values = new Array(contents.length);
    for(let idx in contents) {
      this.values[idx] = new Array();
      for(let item of contents[idx]['items']) {
        this.values[idx].push(item);
      }
    }
  }
}