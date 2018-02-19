import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConfigSurveillanceComponent } from './config-surveillance.component';

const configSurveillanceRoutes: Routes = [
    { path: 'config/surveillance', component: ConfigSurveillanceComponent }
];

@NgModule({
    imports: [RouterModule.forChild(configSurveillanceRoutes)],
    exports: [RouterModule]
})
export class ConfigSurveillanceRoutingModule { }