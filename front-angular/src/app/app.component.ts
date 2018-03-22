import { Component, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { NavigationService } from './services/navigation.service';
import { AuthService } from './services/auth.service';
import { ErrorService } from './services/error.service';
import { SnackBarService } from './services/snack-bar.service';
import { UploadFileService } from './services/upload-file.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  private employeeNumber: string;

  private mymenuVisible: boolean;
  private searchVisible: boolean;
  private meVisible: boolean;

  constructor(
    @Inject('hostname') private hostname: string,
    public nav: NavigationService,
    private http: HttpClient,
    public authService: AuthService,
    private snackBarService: SnackBarService,
    public upload: UploadFileService,
  ) {
    this.nav.hide();
    this.nav.showUserMenu();
    this.mymenuVisible = true;
  }
  ngOnInit() {
  }

  mymenuOpen() {
    this.mymenuVisible = true;
    this.searchVisible = false;
    this.meVisible = false;
  }

  searchOpen() {
    this.mymenuVisible = false;
    this.searchVisible = true;
    this.meVisible = false;
  }

  meOpen() {
    this.mymenuVisible = false;
    this.searchVisible = false;
    this.meVisible = true;
  }

  logout() {
    this.authService.logout()
  }
}
