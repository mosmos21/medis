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
import { ConfigUserComponent } from './config-user/config-user.component';
import { SearchResultComponent } from './search-result/search-result.component';
import { ErrorComponent } from './error/error.component';
import { NewPasswordComponent } from './new-password/new-password.component';
import { NewDocumentComponent } from './new-document/new-document.component';

const routes: Routes = [
  { path: '', redirectTo: 'top', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'top', component: TopComponent, canActivate: [AuthGuardService] },
  { path: 'browsing/:id', component: ViewComponent, canActivate: [AuthGuardService] },
  { path: 'edit', component: SelectDocumentComponent, canActivate: [AuthGuardService] },
  { path: 'new', component: NewDocumentComponent, canActivate: [AuthGuardService] },
  { path: 'edit/:id', component: EditDocumentComponent, canActivate: [AuthGuardService] },
  { path: 'admin/template', component: SelectTemplateComponent, canActivate: [AuthGuardService] },
  { path: 'admin/template/:id', component: EditTemplateComponent, canActivate: [AuthGuardService] },
  { path: 'admin/management', component: UserManagementComponent, canActivate: [AuthGuardService] },
  { path: 'settings/me', component: ConfigUserComponent, canActivate: [AuthGuardService] },
  { path: 'settings/me/notification', component: ConfigNotificationComponent, canActivate: [AuthGuardService] },
  { path: 'settings/me/monitoring_tags', component: ConfigSurveillanceComponent, canActivate: [AuthGuardService] },
  { path: 'search', component: SearchResultComponent, canActivate: [AuthGuardService] },
  { path: 'resetpass', component: NewPasswordComponent, canActivate: [AuthGuardService] },
  { path: 'error/:id', component: ErrorComponent },
  { path: '**', component: ErrorComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
