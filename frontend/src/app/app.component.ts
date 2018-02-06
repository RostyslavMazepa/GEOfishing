import { Component } from '@angular/core';
import { AppService } from './app.service';

@Component({
  // tslint:disable-next-line
  selector: 'body',
  template: '<router-outlet></router-outlet>',
  providers: [ AppService ]
})
export class AppComponent {}
