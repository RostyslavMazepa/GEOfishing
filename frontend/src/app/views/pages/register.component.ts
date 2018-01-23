import {Component, OnInit} from '@angular/core';

import { Facebook } from './register-facebook';
import { Google } from './register-google';

@Component({
  templateUrl: 'register.component.html'
})
export class RegisterComponent implements OnInit {

  facebook: any;
  google: any;

  constructor() { }

  ngOnInit(): void {
    this.facebook = new Facebook('149318269057115'),
    this.google = new Google('834859447878-vj9upm5llmp4asg6ben3b5n4tnjs3d4v.apps.googleusercontent.com', 'googleBtn')
  }

  loginFacebook() {
    this.facebook.login()
  }

  loginGoogle(){
    this.google.login()
  }

}