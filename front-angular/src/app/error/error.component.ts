import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  private errorNum;
  private errorNumUrl;

  constructor(
    route: ActivatedRoute
  ) {
    this.errorNum = route.snapshot.params['id'];
    if(this.errorNum == null) {
      this.errorNum = '404';
    }
    console.log(this.errorNum)
    this.errorNumUrl = '../../assets/image/verySorry' + this.errorNum + '.png'
  }

  ngOnInit() {
  }

}
