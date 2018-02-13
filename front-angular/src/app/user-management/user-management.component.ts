import { Component, OnInit, Inject, HostListener } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {

  private users;
  private result;

  constructor(private http: HttpClient, @Inject('hostname') private hostname: string) { console.log(this.hostname + 'users'); }

  ngOnInit() {
    console.log(this.hostname + 'users');
    this.http.get(this.hostname + 'users').subscribe(
      json => {
        console.log(json);
        this.users = json;
      },
      error => {
        this.users = error;
      }
    );
  }

  changeEnable(index: number) {
    this.users[index]["isEnabled"] = !this.users[index]["isEnabled"];
    console.log(this.users);
  }

  @HostListener('window:unload', [ '$event' ])
  unloadHandler() {
    console.log(this.users);
    this.http.post(this.hostname + "users/update", this.users).subscribe(
      /* postした時の操作があればここにかく */
    );
  }
}
