import { Component } from '@angular/core';
import { AppService } from '../../app.service';

@Component({
  selector: 'app-header',
  templateUrl: './app-header.component.html',
  providers: []
})
export class AppHeaderComponent {
  constructor(private appService: AppService) {}

  logout(){
    this.appService.logout();
  }
}
