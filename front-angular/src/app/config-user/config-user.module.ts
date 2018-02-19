import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfigUserComponent } from './config-user.component';
import { ConfigUserRoutingModule } from './config-user-routing.module';

@NgModule({
  imports: [ ConfigUserRoutingModule, CommonModule],
  declarations: [ ConfigUserComponent ]
})
export class ConfigUserModule { }