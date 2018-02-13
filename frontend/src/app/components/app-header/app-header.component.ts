import {Component} from '@angular/core';
import {AppService} from '../../app.service';
import {HomeService} from '../../views/home/home.service';

@Component({
  selector: 'app-header',
  templateUrl: './app-header.component.html',
  providers: [HomeService]
})
export class AppHeaderComponent {
  constructor(private appService: AppService, private homeService: HomeService) {
  }

  logout() {
    this.appService.logout();
  }

  /*
    setGeolocation() {
      this.homeService.setGeolocation();
    }
  */
}
