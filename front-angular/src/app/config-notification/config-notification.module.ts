import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfigNotificationComponent } from './config-notification.component';
import { ConfigNotificationRoutingModule } from './config-notification-routing.module';

@NgModule({
  imports: [ ConfigNotificationRoutingModule, CommonModule],
  declarations: [ ConfigNotificationComponent ]
})
export class ConfigNotificationModule { }