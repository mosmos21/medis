import { NgModule } from '@angular/core';
import { FormsModule, } from '@angular/forms';
import { LoginComponent } from './login.component';
import { ResetPassComponent } from '../reset-pass/reset-pass.component';
import { LoginRoutingModule } from './login-routing.module';
import { NavigationService } from '../services/navigation.service';
import {
  MatDialogModule,
  MatButtonModule,
  MatInputModule,
  MatIconModule,
} from '@angular/material'

@NgModule({
  imports: [
    LoginRoutingModule,
    FormsModule,
    MatDialogModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
  ],
  declarations: [LoginComponent, ResetPassComponent],
  providers: [NavigationService]
})
export class LoginModule { }