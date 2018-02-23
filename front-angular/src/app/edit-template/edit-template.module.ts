import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DragulaModule } from 'ng2-dragula';
import { EditTemplateComponent } from './edit-template.component';
import { EditTemplateRoutingModule } from './edit-template-routing.module';
import { NavigationService } from '../services/navigation.service';
import { 
  MatButtonModule,
  MatFormFieldModule,
  MatInputModule,
  MatRadioModule,
  MatCheckboxModule,
  MatTabsModule,
  MatCardModule,
  MatIconModule,
  MatDialogModule
} from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    EditTemplateRoutingModule,
    DragulaModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    MatRadioModule,
    MatTabsModule,
    MatCardModule,
    MatIconModule,
  ],
  declarations: [
    EditTemplateComponent,
  ],
  providers: [NavigationService]
})
export class EditTemplateModule { }