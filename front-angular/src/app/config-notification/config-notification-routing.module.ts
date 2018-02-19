import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConfigNotificationComponent }       from './config-notification.component';

const configNotificationRoutes: Routes = [
  { path: 'config/notification', component: ConfigNotificationComponent }
];

@NgModule({
  imports: [ RouterModule.forChild(configNotificationRoutes) ],
  exports: [ RouterModule ]
})
export class ConfigNotificationRoutingModule {}