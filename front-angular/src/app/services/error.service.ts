import { Injectable, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http'

import { AuthService } from './auth.service';

@Injectable()
export class ErrorService {

  constructor(
    @Inject('hostname') private hostname: string,
    private router: Router,
    private authService: AuthService
  ) {}

  errorPath(errorNum: string) {
    this.router.navigate(['error/' + errorNum]);
  }

}
