import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { EditTemplateComponent } from './edit-template.component';

const editTemplateRoutes: Routes = [
    { path: 'admin/template/configure', component: EditTemplateComponent }
];

@NgModule({
    imports: [ RouterModule.forChild(editTemplateRoutes) ],
    exports: [ RouterModule ]
  })
  export class EditTemplateRoutingModule {}