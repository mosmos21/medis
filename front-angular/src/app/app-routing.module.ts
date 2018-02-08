import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { EditTemplateComponent } from './edit-template/edit-template.component';
import { SelectTemplateComponent } from './select-template/select-template.component';

const routes: Routes = [
  { path: '', redirectTo: 'top', pathMatch: 'full'},
  { path: 'login', loadChildren: ' ./login/login.module#LoginModule'},
  { path: 'top', loadChildren: './top/top.module#TopModule'},
  { path: 'admin/template', loadChildren: './select-template/select-template.module#SelectTemplateModule'},
  { path: 'admin/template/configure', loadChildren: './edit-template/edit-template.module#EditTemplateModule'},
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
