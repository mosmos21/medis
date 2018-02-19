import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { EditDocumentComponent } from './edit-document.component';


@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    HttpClientModule
  ],
  declarations: [ EditDocumentComponent ],
  providers: []
})
export class EditDocumentModule {}