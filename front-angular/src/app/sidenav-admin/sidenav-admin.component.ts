import { Component, OnInit } from '@angular/core';
import { NavigationService } from '../services/navigation.service';

@Component({
  selector: 'app-sidenav-admin',
  templateUrl: './sidenav-admin.component.html',
  styleUrls: ['./sidenav-admin.component.css']
})
export class SidenavAdminComponent implements OnInit {

  constructor(public nav: NavigationService) { }

  ngOnInit() {
  }

  logout() {

  }
}
