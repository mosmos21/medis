import { Injectable, Inject } from '@angular/core';
import { Router } from '@angular/router';

@Injectable()
export class ErrorService {

  constructor(private router: Router) { }

  errorPath(errorNum: string) {
    this.router.navigate(['error/' + errorNum]);
  }
}
