import { NgModule }             from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserManagementComponent }       from './user-management.component';
import { UserManagementRoutingModule }   from './user-management-routing.module';

@NgModule({
  imports: [ UserManagementRoutingModule, CommonModule ],
  declarations: [ UserManagementComponent ]
})
export class UserManagementModule { }