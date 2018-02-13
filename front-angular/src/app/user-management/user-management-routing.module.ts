import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserManagementComponent }       from './user-management.component';

const userManagementRoutes: Routes = [
  { path: 'login', component: UserManagementComponent }
];

@NgModule({
  imports: [ RouterModule.forChild(userManagementRoutes) ],
  exports: [ RouterModule ]
})
export class UserManagementRoutingModule {}