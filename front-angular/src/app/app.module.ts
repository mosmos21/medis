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
  MatSnackBarModule,
  MatButtonToggleModule,
  MatTableModule,
  MatSortModule,
  MatGridListModule,
} from '@angular/material';

import { FileUploadModule } from 'ng2-file-upload'

import { NavigationService } from './services/navigation.service';
import { CookieService } from 'ngx-cookie-service'

import { AppComponent } from './app.component';
import { ResetPassComponent } from './reset-pass/reset-pass.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { CreateUsersByCsvComponent } from './create-users-by-csv/create-users-by-csv.component';
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
import { ErrorService } from './services/error.service';
import { SnackBarService } from './services/snack-bar.service';
import { TableService } from './services/table.service';
import { SidenavChildComponent } from './sidenav-child/sidenav-child.component';
import { SearchComponent } from './search/search.component';

import { FormsModule } from '@angular/forms';
import { SidenavAdminComponent } from './sidenav-admin/sidenav-admin.component';
import { MessageModalComponent } from './message-modal/message-modal.component';
import { SearchResultComponent } from './search-result/search-result.component';
import { ErrorComponent } from './error/error.component';
import { NewDocumentComponent } from './new-document/new-document.component';
import { EditIconComponent } from './edit-icon/edit-icon.component';
import { MsgToSidenavService } from './services/msg-to-sidenav.service';
import { IsNewService } from './services/is-new.service';
import { HttpService } from './services/http.service';
import { TypeConversionService } from './services/type-conversion.service';

@NgModule({
  declarations: [
    AppComponent,
    SidenavChildComponent,
    SearchComponent,
    SidenavAdminComponent,
    SearchResultComponent,
    ErrorComponent,
    NewDocumentComponent,
  ],

  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatDialogModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatSnackBarModule,
    MatButtonToggleModule,
    AppRoutingModule,
    MatIconModule,
    MatMenuModule,
    DragulaModule,
    FormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatSortModule,
    MatGridListModule,

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
    TypeConversionService,
    NavigationService,
    CookieService,
    AuthService,
    AuthGuardService,
    SearchService,
    ValidatorService,
    ErrorService,
    SnackBarService,
    MsgToSidenavService,
    IsNewService,
    HttpService,
    TableService,
    // { provide: 'hostname', useValue: 'http://35.196.55.70:8080/v1/' }
    { provide: 'hostname', useValue: 'http://localhost:8080/v1/' }
  ],
  entryComponents: [
    ResetPassComponent,
    ConfirmationComponent,
    CreateUserComponent,
    CreateUsersByCsvComponent,
    InitializationComponent,
    MessageModalComponent,
    EditIconComponent,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
