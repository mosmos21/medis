import { NgModule }             from '@angular/core';
import { FormsModule }          from '@angular/forms';
import { LoginComponent }       from './login.component';
import { LoginRoutingModule }   from './login-routing.module';

@NgModule({
  imports: [ LoginRoutingModule, FormsModule ],
  declarations: [ LoginComponent ],
  providers: []
})
export class LoginModule {}