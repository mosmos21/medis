import { Injectable, Inject } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from './auth.service';

@Injectable()
export class ErrorService {

  constructor(
    private router: Router,
    private authService: AuthService,
  ) { }

  errorPath(errorNum: string) {
    this.authService.errorLogout();
    this.router.navigate(['error/' + errorNum]);
  }
}
