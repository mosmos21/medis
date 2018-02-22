import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'
import { 
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
} from '@angular/material'

import { NewPasswordRoutingModule} from './new-password-routing.module';
import { NewPasswordComponent } from './new-password.component';

@NgModule({
  imports: [
      MatButtonModule,
      MatCardModule,
      MatInputModule,
      MatToolbarModule,
      MatIconModule,
      NewPasswordRoutingModule,
      CommonModule,
      FormsModule,
    ],
  declarations: [ NewPasswordComponent ]
})
export class NewPasswordModule { }