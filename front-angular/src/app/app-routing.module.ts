import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'top', pathMatch: 'full'},
  { path: 'login', loadChildren: ' ./login/login.module#LoginModule'},
  { path: 'top', loadChildren: './top/top.module#TopModule'}

];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
