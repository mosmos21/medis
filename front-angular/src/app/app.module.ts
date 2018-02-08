import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatSidenavModule,
  MatToolbarModule,
  MatListModule,
  MatIconModule,
  MatMenuModule,
} from '@angular/material';

import { NavigationService} from './navigation.service';

import { AppComponent } from './app.component';
import { ResetPassComponent } from './reset-pass/reset-pass.component';

import { LoginModule } from './login/login.module';
import { TopModule } from './top/top.module';
import { AppRoutingModule } from './app-routing.module';


@NgModule({
  declarations: [
    AppComponent,
    ResetPassComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    AppRoutingModule,
    MatIconModule,
    MatMenuModule,

    LoginModule,
    TopModule,

    AppRoutingModule
  ],
  providers: [ NavigationService ],
  entryComponents: [
    ResetPassComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
