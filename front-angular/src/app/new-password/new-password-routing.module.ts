import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewPasswordComponent } from './new-password.component';

const newPasswordRoutes: Routes = [
    { path: 'newpassword', component: NewPasswordComponent }
];

@NgModule({
    imports: [RouterModule.forChild(newPasswordRoutes)],
    exports: [RouterModule]
})
export class NewPasswordRoutingModule { }