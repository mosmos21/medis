import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConfigUserComponent } from './config-user.component';

const configUserRoutes: Routes = [
    { path: 'config/user', component: ConfigUserComponent }
];

@NgModule({
    imports: [RouterModule.forChild(configUserRoutes)],
    exports: [RouterModule]
})
export class ConfigUserRoutingModule { }