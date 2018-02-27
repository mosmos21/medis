import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-top',
  templateUrl: './top.component.html',
  styleUrls: ['./top.component.css']
})
export class TopComponent implements OnInit {

  private updateList: any;
  private ownDocList: any;
  private favDocList: any;
  private MonDocList: any;

  private employeeNumber = 97965;

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
  ) { }

  ngOnInit() {
    this.http.get(this.hostname + "infomations/" + this.employeeNumber).subscribe(
      json => {
        this.updateList = json;
        console.log(this.updateList);
      },
      error => {
        // TODO;
      }
    );
  }

}
