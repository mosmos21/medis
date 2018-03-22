export class Notification {

  public mailNotification: boolean;
  public browserNotification: boolean;
  public tagNotification: any = [];

  constructor() { }

  public setCommentNotification(data: Object): void {
    this.mailNotification = data['mailNotification'];
    this.browserNotification = data['browserNotification'];
  }

  public setTagNotification(data: Object): void {
    this.tagNotification = data;
  }
}