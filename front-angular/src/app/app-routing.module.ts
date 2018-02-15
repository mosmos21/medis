import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TopComponent } from './top/top.component';
import { LoginComponent } from './login/login.component';
import { SelectTemplateComponent } from './select-template/select-template.component';
import { EditTemplateComponent } from './edit-template/edit-template.component';
import { SelectDocumentComponent } from './select-document/select-document.component';
import { EditDocumentComponent } from './edit-document/edit-document.component';
import { AuthGuardService } from './services/auth-guard.service';
import { ViewComponent } from './view/view.component';
import { UserManagementComponent } from './user-management/user-management.component'

const routes: Routes = [
  { path: '', redirectTo: 'top', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'top', component: TopComponent, canActivate: [AuthGuardService] },
  { path: 'browsing/view', component: ViewComponent },
  { path: 'edit', component:SelectDocumentComponent },
  { path: 'edit/:id', component: EditDocumentComponent},
  { path: 'admin/template', component: SelectTemplateComponent },
  { path: 'admin/template/:id', component: EditTemplateComponent },
  { path: 'admin/management', component: UserManagementComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
