import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SelectDocumentComponent } from './select-document.component';
import {
  MatButtonModule,
  MatIconModule
} from '@angular/material';

@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    HttpClientModule,
    MatButtonModule,
    MatIconModule,
  ],
  declarations: [SelectDocumentComponent],
  providers: []
})
export class SelectDocumentModule { }