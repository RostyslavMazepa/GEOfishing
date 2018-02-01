import { Component } from '@angular/core';
import { AppService } from '../../app.service';

@Component({
  selector: 'app-header',
  templateUrl: './app-header.component.html',
  providers: [AppService]
})
export class AppHeaderComponent {
  constructor(private service: AppService){}
  logout(){
    this.service.logout();
  }
}
