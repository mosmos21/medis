import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewComponent } from './view.component';
import { ViewRoutingModule } from './view-routing.module';
import {
  MatCheckboxModule,
  MatRadioModule,
  MAT_CHECKBOX_CLICK_ACTION,
  MatButtonModule,
  MatToolbarModule,
  MatFormFieldModule,
  MatInputModule,
  MatIconModule,
  MatDividerModule,
  MatButtonToggleModule,
} from '@angular/material';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    ViewRoutingModule,
    CommonModule,
    MatCheckboxModule,
    MatRadioModule,
    MatButtonModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatIconModule,
    MatDividerModule,
    MatButtonToggleModule,
  ],
  declarations: [ViewComponent],
  providers: [
    { provide: MAT_CHECKBOX_CLICK_ACTION, useValue: 'noop' },
  ]
})
export class ViewModule { }