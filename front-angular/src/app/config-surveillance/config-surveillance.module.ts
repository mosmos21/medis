import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfigSurveillanceComponent } from './config-surveillance.component';
import { ConfigSurveillanceRoutingModule } from './config-surveillance-routing.module';

@NgModule({
  imports: [ ConfigSurveillanceRoutingModule, CommonModule],
  declarations: [ ConfigSurveillanceComponent ]
})
export class ConfigSurveillanceModule { }