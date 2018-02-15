import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserManagementComponent } from './user-management.component';
import { ConfirmationComponent } from '../confirmation/confirmation.component';
import { CreateUserComponent } from '../create-user/create-user.component'
import { UserManagementRoutingModule } from './user-management-routing.module';
import {
  MatButtonModule,
  MatInputModule,
  MatSelectModule
} from '@angular/material';

@NgModule({
  imports: [
    UserManagementRoutingModule,
    FormsModule,
    CommonModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule
  ],
  declarations: [
    UserManagementComponent,
    ConfirmationComponent,
    CreateUserComponent,
  ]
})
export class UserManagementModule { }