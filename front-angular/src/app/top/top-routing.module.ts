import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TopComponent }       from './top.component';

const topRoutes: Routes = [
  { path: 'top', component: TopComponent }
];

@NgModule({
  imports: [ RouterModule.forChild(topRoutes) ],
  exports: [ RouterModule ]
})
export class TopRoutingModule {}