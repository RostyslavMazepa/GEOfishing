import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { Facebook } from './register-facebook';
import { Google } from './register-google';

@Component({
  templateUrl: 'register.component.html'
})
export class RegisterComponent implements OnInit {

  facebook: any;
  google: any;

  private userName: string;
  private userEmail: string;

    // Create form
    registerForm = new FormGroup({
        userNameInput: new FormControl('', Validators.required),
        userEmailInput: new FormControl('', Validators.required),
        passwordInput: new FormControl('', Validators.required),
        repeatPasswordInput: new FormControl('', Validators.required)
    });

  constructor() { }

  ngOnInit(): void {
    this.facebook = new Facebook('149318269057115');
    this.google = new Google('834859447878-vj9upm5llmp4asg6ben3b5n4tnjs3d4v.apps.googleusercontent.com', 'googleBtn');
  }

  loginFacebook() {
    this.facebook.login();
  }

  loginGoogle(){
    this.google.login();
    this.registerForm.setValue({
        userNameInput: this.google.getUserName(),
        userEmailInput: this.google.getUserEmail(),
        passwordInput: this.registerForm.get('passwordInput').value.trim(),
        repeatPasswordInput: this.registerForm.get('repeatPasswordInput').value.trim()
      });
  }

}