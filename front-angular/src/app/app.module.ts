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
} from '@angular/material';

import { NavigationService} from './services/navigation.service';

import { AppComponent } from './app.component';
import { ResetPassComponent } from './reset-pass/reset-pass.component';

import { LoginModule } from './login/login.module';
import { TopModule } from './top/top.module';
import { EditTemplateModule } from './edit-template/edit-template.module';
import { SelectTemplateModule } from './select-template/select-template.module'

import { AppRoutingModule } from './app-routing.module';
<<<<<<< Updated upstream
import { AuthService } from './services/auth.service';
import { AuthGuardService } from './services/auth-guard.service';
=======
>>>>>>> Stashed changes
import { SidenavChildComponent } from './sidenav-child/sidenav-child.component';

@NgModule({
  declarations: [
    AppComponent,
    ResetPassComponent,
    SidenavChildComponent,
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

    LoginModule,
    TopModule,
    EditTemplateModule,
    SelectTemplateModule,

    AppRoutingModule
  ],
  providers: [
    NavigationService,
    AuthService,
    AuthGuardService,
    {provide: 'hostname', useValue: 'http://localhost:8080/mock/'}
  ],
  entryComponents: [
    ResetPassComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
