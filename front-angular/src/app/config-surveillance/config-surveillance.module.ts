import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfigSurveillanceComponent } from './config-surveillance.component';
import { ConfigSurveillanceRoutingModule } from './config-surveillance-routing.module';
import { FormsModule } from '@angular/forms';
import {
  MatFormFieldModule,
  MatInputModule,
  MatButtonModule,
} from '@angular/material';

@NgModule({
  imports: [
    ConfigSurveillanceRoutingModule,
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  declarations: [ConfigSurveillanceComponent]
})
export class ConfigSurveillanceModule { }