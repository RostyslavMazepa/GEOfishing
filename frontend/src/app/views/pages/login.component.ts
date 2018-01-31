import { Component } from '@angular/core';
import { AppService } from '../../app.service';

@Component({
  templateUrl: 'login.component.html',
  providers: [AppService]
})
export class LoginComponent {

  public user = {username: '', password: ''};

  constructor(private service: AppService) {}

  login(){
    this.service.obtainAccessToken(this.user);
  }
}
