import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConfigTopComponent } from './config-top.component';

const configTopRoutes: Routes = [
    { path: 'config/top', component: ConfigTopComponent }
];

@NgModule({
    imports: [RouterModule.forChild(configTopRoutes)],
    exports: [RouterModule]
})
export class ConfigTopRoutingModule { }