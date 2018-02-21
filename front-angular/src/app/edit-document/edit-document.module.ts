import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { EditDocumentComponent } from './edit-document.component';
import { DragulaModule } from 'ng2-dragula';
import {
  MatButtonModule,
  MatTabsModule,
  MatFormFieldModule,
  MatInputModule,
} from '@angular/material';


@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    HttpClientModule,
    MatButtonModule,
    MatTabsModule,
    MatFormFieldModule,
    MatInputModule,
    DragulaModule,
  ],
  declarations: [EditDocumentComponent],
  providers: []
})
export class EditDocumentModule { }