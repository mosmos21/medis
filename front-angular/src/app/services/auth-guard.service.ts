import { Injectable }       from '@angular/core';
import {
  CanActivate, Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot
}                           from '@angular/router';
import { AuthService }      from './auth.service';
import { NavigationService } from './navigation.service';

@Injectable()
export class AuthGuardService implements CanActivate {
  constructor(private authService: AuthService, private router: Router, private nav: NavigationService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    let url: string = state.url;

    return this.checkLogin(url);
  }

  checkLogin(url: string): boolean {
    if (this.authService.isLoggedIn) {
      this.nav.show();
      return true; 
    }

    // Store the attempted URL for redirecting
    this.authService.redirectUrl = url;

    this.nav.hide();
    // Navigate to the login page with extras
    this.router.navigate(['/login']);
    return false;
  }
}