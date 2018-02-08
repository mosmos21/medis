import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';

import { ResetPassComponent } from '../reset-pass/reset-pass.component';

import { NavigationService } from '../services/navigation.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(public dialog: MatDialog, private nav: NavigationService) {
    this.nav.hide();
  }  

  openDialog(): void {
    let dialogRef = this.dialog.open(ResetPassComponent, {
      width: '500px'
    });
  }

  ngOnInit() {
  }

}
