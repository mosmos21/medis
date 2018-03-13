import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfigUserComponent } from './config-user.component';
import { ConfigUserRoutingModule } from './config-user-routing.module';
import { EditIconComponent } from '../edit-icon/edit-icon.component';
import { FormsModule } from '@angular/forms';
import {
  MatFormFieldModule,
  MatInputModule,
  MatButtonModule,
  MatIconModule
} from '@angular/material';
import { 
  FileUploadModule,
  FileSelectDirective,
  FileDropDirective,
} from 'ng2-file-upload'

@NgModule({
  imports: [
    ConfigUserRoutingModule,
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
  ],
  declarations: [
    ConfigUserComponent,
    EditIconComponent,
    FileDropDirective,
    FileSelectDirective,
  ]
})
export class ConfigUserModule { }