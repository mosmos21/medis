import { NgModule }             from '@angular/core';
import { FormsModule }          from '@angular/forms';
import { LoginComponent }       from './login.component';
import { LoginRoutingModule }   from './login-routing.module';
import { NavigationService }    from '../services/navigation.service';

@NgModule({
  imports: [ LoginRoutingModule, FormsModule ],
  declarations: [ LoginComponent ],
  providers: [ NavigationService ]
})
export class LoginModule {}