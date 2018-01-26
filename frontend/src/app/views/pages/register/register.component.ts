import { Component} from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { appIdGoogle, elementIdGoogle, msTimeoutGoogle, appIdFacebook } from "../../../../environments/environment";
import { Facebook } from './register-facebook';
import { Google } from './register-google';


@Component({
  templateUrl: 'register.component.html'
})
export class RegisterComponent {

  facebook: any;
  google: any;

  private userImageURL: string;

    // Create form
  registerForm = new FormGroup({
    userNameInput: new FormControl('', Validators.required),
    userEmailInput: new FormControl('', Validators.required),
    passwordInput: new FormControl('', Validators.required),
    repeatPasswordInput: new FormControl('', Validators.required)
  });

  constructor() {
      this.facebook = new Facebook(appIdFacebook);
      this.google = new Google(appIdGoogle, elementIdGoogle);
  }


  loginFacebook() {
    this.facebook.login();
  }


  loginGoogle(){
      let promise = new Promise(
          (resolve, reject) => {
              setTimeout(() => {
                  resolve('result')
              }, msTimeoutGoogle);
          })
      promise.then(
              result => {
                    this.registerForm.setValue({
                        userNameInput: this.google.socialNetworkInfo[0].userName,
                        userEmailInput: this.google.socialNetworkInfo[0].userEmail,
                        passwordInput: this.registerForm.get('passwordInput').value.trim(),
                        repeatPasswordInput: this.registerForm.get('repeatPasswordInput').value.trim()
                    });
                  this.userImageURL = this.google.socialNetworkInfo[0].userImageURL;
              },
              error => {
                  console.log('error')
              })
  }
}