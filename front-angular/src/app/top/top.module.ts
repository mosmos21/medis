import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopRoutingModule } from './top-routing.module';
import { TopComponent } from './top.component';
import {
  MatIconModule,
  MatTableModule,
  MatSortModule,
  MatGridListModule,
} from '@angular/material';

@NgModule({
  imports: [
    TopRoutingModule,
    CommonModule,
    MatIconModule,
    MatTableModule,
    MatSortModule,
    MatGridListModule,
  ],
  declarations: [TopComponent]
})
export class TopModule { }
