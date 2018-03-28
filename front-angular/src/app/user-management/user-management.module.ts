import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PapaParseModule } from 'ngx-papaparse';
import { UserManagementComponent } from './user-management.component';
import { ConfirmationComponent } from '../confirmation/confirmation.component';
import { CreateUserComponent } from '../create-user/create-user.component'
import { CreateUsersByCsvComponent } from '../create-users-by-csv/create-users-by-csv.component'
import { MessageModalComponent } from '../message-modal/message-modal.component'
import { UserManagementRoutingModule } from './user-management-routing.module';
import { InitializationComponent } from '../initialization/initialization.component'
import {
  MatDialogModule,
  MatButtonModule,
  MatInputModule,
  MatSelectModule,
  MatSlideToggleModule,
  MatToolbarModule,
  MatFormFieldModule,
  MatSortModule,
  MatTableModule,
  MatIconModule,
} from '@angular/material';

@NgModule({
  imports: [
    UserManagementRoutingModule,
    FormsModule,
    CommonModule,
    PapaParseModule,
    MatDialogModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatSlideToggleModule,
    MatFormFieldModule,
    MatInputModule,
    MatSortModule,
    MatTableModule,
    MatIconModule,
  ],
  declarations: [
    UserManagementComponent,
    ConfirmationComponent,
    CreateUserComponent,
    CreateUsersByCsvComponent,
    MessageModalComponent,
    InitializationComponent,
  ]
})
export class UserManagementModule { }