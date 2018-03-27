import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SelectTemplateComponent } from './select-template.component'
import { SelectTemplateRoutingModule } from './select-template-routing.module'
import {
  MatDialogModule,
  MatButtonModule,
  MatInputModule,
  MatSelectModule,
  MatSlideToggleModule,
  MatToolbarModule,
  MatFormFieldModule,
  MatSortModule,
  MatTableModule,
  MatIconModule
} from '@angular/material';

@NgModule({
  imports: [
    SelectTemplateRoutingModule,
    FormsModule,
    CommonModule,
    HttpClientModule,
    MatDialogModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatSlideToggleModule,
    MatFormFieldModule,
    MatInputModule,
    MatSortModule,
    MatTableModule,
    MatIconModule
  ],
  declarations: [
    SelectTemplateComponent,
  ],
  providers: []
})
export class SelectTemplateModule { }