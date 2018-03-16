import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopRoutingModule } from './top-routing.module';
import { TopComponent } from './top.component';
import { MatIconModule } from '@angular/material';

@NgModule({
  imports: [
    TopRoutingModule,
    CommonModule,
    MatIconModule,
  ],
  declarations: [TopComponent]
})
export class TopModule { }
