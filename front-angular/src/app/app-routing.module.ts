import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TopComponent } from './top/top.component';
import { LoginComponent } from './login/login.component';
import { SelectTemplateComponent } from './select-template/select-template.component';
import { EditTemplateComponent } from './edit-template/edit-template.component';

const routes: Routes = [
  { path: '', redirectTo: 'top', pathMatch: 'full'},
  { path: 'login', component: LoginComponent},
  { path: 'top', component: TopComponent },
  { path: 'admin/template', component: SelectTemplateComponent},
  { path: 'admin/template/configure', component: EditTemplateComponent},
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
