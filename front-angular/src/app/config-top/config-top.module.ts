import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfigTopComponent } from './config-top.component';
import { ConfigTopRoutingModule } from './config-top-routing.module';

@NgModule({
  imports: [ ConfigTopRoutingModule, CommonModule],
  declarations: [ ConfigTopComponent ]
})
export class ConfigTopModule { }