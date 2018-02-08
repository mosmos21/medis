import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TopComponent } from './top/top.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  { path: '', redirectTo: 'top', pathMatch: 'full'},
  { path: 'login', component: LoginComponent},
  { path: 'top', component: TopComponent },
  { path: 'admin/template/configure', loadChildren: './edit-template/edit-template.module#EditTemplateModule'}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
