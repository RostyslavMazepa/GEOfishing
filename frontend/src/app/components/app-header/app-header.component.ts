import { Component } from '@angular/core';
import { LoginService } from '../../views/pages/login/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './app-header.component.html',
  providers: [ LoginService ]
})
export class AppHeaderComponent {
  constructor(private service: LoginService){}
  logout(){
    this.service.logout();
  }
}
