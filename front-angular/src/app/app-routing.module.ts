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
import { ConfigNotificationComponent } from './config-notification/config-notification.component';
import { ConfigSurveillanceComponent } from './config-surveillance/config-surveillance.component';
import { ConfigTopComponent } from './config-top/config-top.component';
import { ConfigUserComponent } from './config-user/config-user.component';
import { SearchResultComponent } from './search-result/search-result.component';
import { PageNotFoundComponent } from './error/page-not-found.component'
import { NewPasswordComponent } from './new-password/new-password.component';

const routes: Routes = [
  { path: '', redirectTo: 'top', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'top', component: TopComponent, canActivate: [AuthGuardService] },
  { path: 'browsing/:id', component: ViewComponent },
  { path: 'edit', component: SelectDocumentComponent },
  { path: 'edit/:id', component: EditDocumentComponent },
  { path: 'admin/template', component: SelectTemplateComponent },
  { path: 'admin/template/:id', component: EditTemplateComponent },
  { path: 'admin/management', component: UserManagementComponent },
  { path: 'settings/me', component: ConfigUserComponent },
  { path: 'settings/me/notification', component: ConfigNotificationComponent },
  { path: 'settings/me/toppage', component: ConfigTopComponent },
  { path: 'settings/me/monitoring_tags', component: ConfigSurveillanceComponent },
  { path: 'search', component: SearchResultComponent },
  { path: 'resetpass', component: NewPasswordComponent },
  { path: '**', component:PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
