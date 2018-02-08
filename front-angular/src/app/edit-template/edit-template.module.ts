import { NgModule }             from '@angular/core';
import { FormsModule }          from '@angular/forms';
import { EditTemplateComponent } from './edit-template.component';
import { EditTemplateRoutingModule } from './edit-template-routing.module';

@NgModule({
    imports: [ EditTemplateRoutingModule, FormsModule ],
    declarations: [ EditTemplateComponent ],
    providers: []
})
export class EditTemplateModule {}