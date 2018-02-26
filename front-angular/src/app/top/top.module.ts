import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopRoutingModule } from './top-routing.module';
import { TopComponent } from './top.component';

@NgModule({
  imports: [TopRoutingModule, CommonModule],
  declarations: [TopComponent]
})
export class TopModule { }
