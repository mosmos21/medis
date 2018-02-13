import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SelectTemplateComponent } from './select-template.component'
import { SelectTemplateRoutingModule } from './select-template-routing.module'

@NgModule({
  imports: [
    SelectTemplateRoutingModule, 
    FormsModule,
    CommonModule,
    HttpClientModule
  ],
  declarations: [ SelectTemplateComponent ],
  providers: []
})
export class SelectTemplateModule {}