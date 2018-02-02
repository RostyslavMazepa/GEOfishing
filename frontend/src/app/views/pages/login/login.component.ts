import { Component } from '@angular/core';
import { LoginService } from './login.service';

@Component({
  templateUrl: 'login.component.html',
  providers: [ LoginService ]
})
export class LoginComponent {

  public user = {username: '', password: ''};

  constructor(private service: LoginService) {}

  login(){
    this.service.obtainAccessToken(this.user);
  }
}
