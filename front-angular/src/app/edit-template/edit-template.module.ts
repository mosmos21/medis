import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DragulaModule } from 'ng2-dragula';
import { EditTemplateComponent } from './edit-template.component';
import { EditTemplateRoutingModule } from './edit-template-routing.module';

@NgModule({
  imports: [
    FormsModule,
    EditTemplateRoutingModule,
    DragulaModule
  ],
  declarations: [EditTemplateComponent],
  providers: []
})
export class EditTemplateModule { }