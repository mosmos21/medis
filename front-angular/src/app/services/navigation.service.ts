import { Injectable } from '@angular/core';

@Injectable()
export class NavigationService {
  visible: boolean;

  constructor() { 
    this.visible=false;
  }

  show(){
    this.visible = true;
  }

  hide() {
    this.visible = false;
  }

}
