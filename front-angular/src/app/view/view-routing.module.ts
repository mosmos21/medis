import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ViewComponent }       from './view.component';

const viewRoutes: Routes = [
  { path: 'browsiong/view', component: ViewComponent }
];

@NgModule({
  imports: [ RouterModule.forChild(viewRoutes) ],
  exports: [ RouterModule ]
})
export class ViewRoutingModule {}