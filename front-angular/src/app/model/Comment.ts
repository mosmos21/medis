export class Comment {

  public commentId: string;
  public employeeNumber: string;
  public lastName: string;
  public firstName: string;
  public commentDate: number;
  public content: string;
  public icon: boolean;
  public read: boolean;
  public alreadyRead: string;

  constructor(data?: Object) {
    if (data == null) return;
    this.commentId = data['commentId'];
    this.employeeNumber = data['employeeNumber'];
    this.lastName = data['lastName'];
    this.firstName = data['firstName'];
    this.commentDate = data['commentDate'];
    this.content = data['commentContent'];
    this.icon = data['icon'];
    this.read = data['read'];
    this.alreadyRead = 'unread';
  }

  setAlreadyRead(documentAuthor: string, loginUser: string): void {
    if (this.read) {
      this.alreadyRead = 'read';
    } else if (documentAuthor == loginUser) {
      this.alreadyRead = 'unreadMe';
    } else {
      this.alreadyRead = 'unread';
    }
  }

  updateToRead(): void {
    this.read = true;
    this.alreadyRead = 'read';
  }
}