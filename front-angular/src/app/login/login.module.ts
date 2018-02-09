import { NgModule }             from '@angular/core';
import { FormsModule }          from '@angular/forms';
import { LoginComponent }       from './login.component';
import { ResetPassComponent } from '../reset-pass/reset-pass.component';
import { LoginRoutingModule }   from './login-routing.module';
import { NavigationService }    from '../services/navigation.service';

@NgModule({
  imports: [ LoginRoutingModule, FormsModule ],
  declarations: [ LoginComponent, ResetPassComponent ],
  providers: [ NavigationService ]
})
export class LoginModule {}