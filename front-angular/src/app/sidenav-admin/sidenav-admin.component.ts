import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http'

import { AuthService } from '../services/auth.service';
import { NavigationService } from '../services/navigation.service';

@Component({
  selector: 'app-sidenav-admin',
  templateUrl: './sidenav-admin.component.html',
  styleUrls: ['./sidenav-admin.component.css']
})
export class SidenavAdminComponent implements OnInit {

  constructor(
    @Inject('hostname') private hostname: string,
    public http: HttpClient,
    public nav: NavigationService,
    public authService: AuthService
  ) { }

  ngOnInit() {
  }
}
