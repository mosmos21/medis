import { NgModule }             from '@angular/core';
import { FormsModule }          from '@angular/forms';
import { SelectTemplateComponent }       from './select-template.component'
import { SelectTemplateRoutingModule }   from './select-template-routing.module'

@NgModule({
  imports: [ SelectTemplateRoutingModule, FormsModule ],
  declarations: [ SelectTemplateComponent ],
  providers: []
})
export class SelectTemplateModule {}