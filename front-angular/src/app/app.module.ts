import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatDialogModule,
  MatSidenavModule,
  MatToolbarModule,
  MatListModule,
  MatIconModule,
  MatMenuModule,
  MatInputModule,
  MatFormFieldModule,
  MatProgressSpinnerModule,
} from '@angular/material';

import { NavigationService } from './services/navigation.service';

import { AppComponent } from './app.component';
import { ResetPassComponent } from './reset-pass/reset-pass.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { InitializationComponent } from './initialization/initialization.component';

import { DragulaModule } from 'ng2-dragula';

import { LoginModule } from './login/login.module';
import { TopModule } from './top/top.module';
import { EditTemplateModule } from './edit-template/edit-template.module';
import { SelectTemplateModule } from './select-template/select-template.module';
import { SelectDocumentModule } from './select-document/select-document.module';
import { EditDocumentModule } from './edit-document/edit-document.module';
import { ViewModule } from './view/view.module';
import { UserManagementModule } from './user-management/user-management.module';
import { ConfigNotificationModule } from './config-notification/config-notification.module'
import { ConfigSurveillanceModule } from './config-surveillance/config-surveillance.module';
import { ConfigUserModule } from './config-user/config-user.module';
import { NewPasswordModule } from './new-password/new-password.module';

import { AppRoutingModule } from './app-routing.module';
import { ConvertDateService } from './services/convert-date.service';
import { AuthService } from './services/auth.service';
import { AuthGuardService } from './services/auth-guard.service';
import { SearchService } from './services/search.service';
import { ValidatorService } from './services/validator.service';
import { SidenavChildComponent } from './sidenav-child/sidenav-child.component';
import { SearchComponent } from './search/search.component';

import { FormsModule } from '@angular/forms';
import { SidenavAdminComponent } from './sidenav-admin/sidenav-admin.component';
import { MessageModalComponent } from './message-modal/message-modal.component';
import { SearchResultComponent } from './search-result/search-result.component';
import { PageNotFoundComponent } from './error/page-not-found.component';


@NgModule({
  declarations: [
    AppComponent,
    SidenavChildComponent,
    SearchComponent,
    SidenavAdminComponent,
    SearchResultComponent,
    PageNotFoundComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatDialogModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    AppRoutingModule,
    MatIconModule,
    MatMenuModule,
    DragulaModule,
    FormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,

    LoginModule,
    TopModule,
    EditTemplateModule,
    SelectTemplateModule,
    SelectDocumentModule,
    EditDocumentModule,
    ViewModule,
    UserManagementModule,
    ConfigNotificationModule,
    ConfigSurveillanceModule,
    ConfigUserModule,
    NewPasswordModule,

    AppRoutingModule
  ],
  providers: [
    ConvertDateService,
    NavigationService,
    AuthService,
    AuthGuardService,
    SearchService,
    ValidatorService,
    { provide: 'hostname', useValue: 'http://localhost:8080/v1/' }
  ],
  entryComponents: [
    ResetPassComponent,
    ConfirmationComponent,
    CreateUserComponent,
    InitializationComponent,
    MessageModalComponent,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
