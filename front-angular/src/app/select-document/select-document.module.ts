import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SelectDocumentComponent } from './select-document.component';


@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    HttpClientModule
  ],
  declarations: [ SelectDocumentComponent ],
  providers: []
})
export class SelectDocumentModule {}