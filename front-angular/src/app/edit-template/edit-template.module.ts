import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DragulaModule } from 'ng2-dragula';
import { EditTemplateComponent } from './edit-template.component';
import { EditTemplateRoutingModule } from './edit-template-routing.module';
import { MatButtonModule, MatFormFieldModule, MatInputModule, MatTabsModule, MatCardModule, MatIconModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    EditTemplateRoutingModule,
    DragulaModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatTabsModule,
    MatCardModule,
    MatIconModule
  ],
  declarations: [EditTemplateComponent],
  providers: []
})
export class EditTemplateModule { }