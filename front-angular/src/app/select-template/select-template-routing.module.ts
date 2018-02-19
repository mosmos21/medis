import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SelectTemplateComponent }       from './select-template.component';

const selectTemplateRoutes: Routes = [
  { path: 'admin/template', component: SelectTemplateComponent }
];

@NgModule({
  imports: [ RouterModule.forChild(selectTemplateRoutes) ],
  exports: [ RouterModule ]
})
export class SelectTemplateRoutingModule {}