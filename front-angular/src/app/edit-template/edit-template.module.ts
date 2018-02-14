import { NgModule } from '@angular/core';
import { CommonModule} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DragulaModule } from 'ng2-dragula';
import { EditTemplateComponent } from './edit-template.component';
import { EditTemplateRoutingModule } from './edit-template-routing.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    EditTemplateRoutingModule,
    DragulaModule
  ],
  declarations: [EditTemplateComponent],
  providers: []
})
export class EditTemplateModule { }