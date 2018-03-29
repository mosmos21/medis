import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ConfigNotificationComponent } from './config-notification.component';
import { ConfigNotificationRoutingModule } from './config-notification-routing.module';
import {
  MatButtonModule,
  MatSlideToggleModule,
  MatSortModule,
  MatTableModule,
} from '@angular/material';

@NgModule({
  imports: [
    ConfigNotificationRoutingModule,
    FormsModule,
    CommonModule,
    MatButtonModule,
    MatSlideToggleModule,
    MatSortModule,
    MatTableModule,
  ],
  declarations: [ConfigNotificationComponent]
})
export class ConfigNotificationModule { }