import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserManagementComponent } from './user-management.component';
import { ConfirmationComponent } from '../confirmation/confirmation.component';
import { CreateUserComponent } from '../create-user/create-user.component'
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
} from '@angular/material';

@NgModule({
  imports: [
    UserManagementRoutingModule,
    FormsModule,
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatSlideToggleModule,
    MatFormFieldModule,
    MatInputModule
  ],
  declarations: [
    UserManagementComponent,
    ConfirmationComponent,
    CreateUserComponent,
    InitializationComponent,
  ]
})
export class UserManagementModule { }