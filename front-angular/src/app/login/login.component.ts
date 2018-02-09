import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material';

import { ResetPassComponent } from '../reset-pass/reset-pass.component';

import { NavigationService } from '../services/navigation.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  errorMessage: string = '';

  employeeNumber: string = '';
  password: string = '';

  constructor(
    public authService: AuthService, public router: Router,
    public dialog: MatDialog, private nav: NavigationService) {
    this.nav.hide();
  }  

  openDialog(): void {
    let dialogRef = this.dialog.open(ResetPassComponent, {
      width: '500px'
    });
  }

  ngOnInit() {
  }

  login() {
    this.authService.login(this.employeeNumber, this.password).subscribe(() => {
      if(this.authService.isLoggedIn) {
        let redirect = this.authService.redirectUrl ? this.authService.redirectUrl : '/top';
        this.router.navigate([redirect]);
      } else {
        this.errorMessage = this.authService.message;
        console.log(this.errorMessage);
      }
    });
  }

  logout() {
    this.authService.logout();
  }
}
