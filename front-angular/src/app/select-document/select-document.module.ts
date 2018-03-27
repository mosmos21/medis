import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SelectDocumentComponent } from './select-document.component';
import {
  MatButtonModule,
  MatIconModule,
  MatTableModule,
  MatSortModule,
} from '@angular/material';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    HttpClientModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatSortModule,
    RouterModule,
  ],
  declarations: [SelectDocumentComponent],
  providers: []
})
export class SelectDocumentModule { }