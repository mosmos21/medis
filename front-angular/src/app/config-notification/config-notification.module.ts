import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfigNotificationComponent } from './config-notification.component';
import { ConfigNotificationRoutingModule } from './config-notification-routing.module';
import {
  MatButtonModule,
  MatSlideToggleModule,
} from '@angular/material';

@NgModule({
  imports: [
    ConfigNotificationRoutingModule,
    CommonModule,
    MatButtonModule,
    MatSlideToggleModule,
  ],
  declarations: [ConfigNotificationComponent]
})
export class ConfigNotificationModule { }