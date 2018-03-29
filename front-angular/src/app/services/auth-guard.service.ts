import { Injectable } from '@angular/core';
import {
  CanActivate, Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot
} from '@angular/router';
import { AuthService } from './auth.service';
import { NavigationService } from './navigation.service';
import { HttpService } from './http.service';

@Injectable()
export class AuthGuardService implements CanActivate {

  constructor(
    private nav: NavigationService,
    private http: HttpService,
    private router: Router,
    private authService: AuthService,
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    let url: string = state.url;

    return this.checkLogin(url);
  }

  checkLogin(url: string): boolean {
    if (this.authService.isLoggedIn()) {
      this.http.get('system/me').subscribe(res=> {
        if(url.startsWith('/admin') && res['authorityId'] == 'a0000000001') {
          this.logout();
        }
      },error =>{
        this.logout();
      });
      this.nav.show();
      return true;
    }

    this.authService.redirectUrl = url;
    this.nav.hide();
    this.router.navigate(['/login']);
    return false;
  }

  logout(): void {
    this.authService.logout();
    this.nav.hide();
    this.router.navigate(['/login']);
  }
}