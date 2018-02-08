import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatSidenavModule,
  MatDialogModule
} from '@angular/material';


import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { ResetPassComponent } from './reset-pass/reset-pass.component';

import { AppRoutingModule } from './app-routing.module';
import { LoginModule } from './login/login.module';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    ResetPassComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatSidenavModule,
    MatDialogModule,

    LoginModule,

    AppRoutingModule
  ],
  providers: [],
  entryComponents: [ResetPassComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
