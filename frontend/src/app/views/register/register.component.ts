import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {SocialNetworkInfo} from './SocialNetworkInfo';

import {
  appIdGoogle,
  elementIdGoogle,
  appIdFacebook
} from '../../../environments/environment';
import {Facebook} from './register-facebook';
import {Google} from './register-google';
import {RegisterService} from './register.service';
import {RegisterUser} from '../home/RegisterUser';

@Component({
  templateUrl: 'register.component.html',
  providers: [RegisterService]
})
export class RegisterComponent {

  facebook: any;
  google: any;

  // public userName: string;
  // public userEmail: string;

  public userImageURL: string;

  statusCode: number;
  requestProcessing = false;
  socialNetworkInfo: SocialNetworkInfo;
  processValidation = false;
  // Create form
  registerForm = new FormGroup({
    userName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    firstName: new FormControl('', Validators.required),
    userEmail: new FormControl('', Validators.required),
    userPassword: new FormControl('', Validators.required),
    userRepeatPassword: new FormControl('', Validators.required)
  });

  constructor(private registerService: RegisterService) {
    this.facebook = new Facebook(appIdFacebook);
    this.google = new Google(appIdGoogle, elementIdGoogle);
  }

  createAccount() {
    this.processValidation = true;
    if (this.registerForm.invalid) {
      return;
    }
    this.preProcessConfigurations();
    const userName = this.registerForm.get('userName').value.trim();
    const lastName = this.registerForm.get('lastName').value.trim();
    const firstName = this.registerForm.get('firstName').value.trim();
    const userEmail = this.registerForm.get('userEmail').value.trim();
    const userPassword = this.registerForm.get('userPassword').value.trim();

    const registerUser = new RegisterUser(userName, lastName, firstName, userEmail, userPassword);
    this.registerService.createAccount(registerUser)
      .subscribe(
        successCode => {
          this.statusCode = successCode;
          console.log(successCode)
        },
        errorCode => {
          this.statusCode = errorCode
          console.log('Error - ' + errorCode)
        }
      );
  }

  loginFacebook() {

    this.facebook.response
      .subscribe(
        (data) => {
          if (data.status === 'connected') {
            // console.log(data);
            // console.log(this.socialNetworkInfo);
            this.socialNetworkInfo = {
              socialNetwork: 'facebook',
              userName: null,
              userEmail: null,
              userToken: data.authResponse.accessToken,
              userId: data.authResponse.userID,
              userImageURL: null,
              expiresIn: data.authResponse.expiresIn,
              signedRequest: data.authResponse.signedRequest
            };
            // console.log(this.socialNetworkInfo);
            this.facebook.login()
          }
        },
        (err) => { console.log(err) }
      );

    this.facebook.responseUser
      .subscribe(
        (dataUser) => {
          if (dataUser) {
            // console.log(dataUser)
            // console.log(this.socialNetworkInfo)
            this.socialNetworkInfo = {
              socialNetwork: this.socialNetworkInfo.socialNetwork,
              userName: dataUser.name,
              userEmail: dataUser.email,
              userToken: this.socialNetworkInfo.userToken,
              userId: this.socialNetworkInfo.userId,
              userImageURL: dataUser.picture.data.url,
              expiresIn: this.socialNetworkInfo.expiresIn,
              signedRequest: this.socialNetworkInfo.signedRequest
            };
            // console.log(this.socialNetworkInfo);
            this.registerForm.setValue({
              userName: this.socialNetworkInfo.userName,
              lastName: null,
              firstName: null,
              userEmail: this.socialNetworkInfo.userEmail || null,
              userPassword: this.registerForm.get('userPassword').value.trim(),
              userRepeatPassword: this.registerForm.get('userRepeatPassword').value.trim()
            });
            this.userImageURL = dataUser.picture.data.url;
            // console.log(this.socialNetworkInfo)
          }

        },
        (err) => { console.log(err) }
      );
  }


  loginGoogle() {
    this.google.response
      .subscribe(
        (data) => {
          if (data) {
            // console.log(data)

            this.socialNetworkInfo = {
              socialNetwork: data['Zi']['idpId'],
              userName: data['w3']['ig'],
              userEmail: data['w3']['U3'],
              userToken: data['Zi']['id_token'],
              userId: data['w3']['Eea'],
              userImageURL: data['w3']['Paa'],
              expiresIn: data['Zi']['expires_in'],
              signedRequest: data['Zi']['expires_at']
            };

            this.registerForm.setValue({
              userName: this.socialNetworkInfo.userName,
              lastName: data['w3']['wea'],
              firstName: data['w3']['ofa'],
              userEmail: this.socialNetworkInfo.userEmail,
              userPassword: this.registerForm.get('userPassword').value.trim(),
              userRepeatPassword: this.registerForm.get('userRepeatPassword').value.trim()
            });
            this.userImageURL = this.socialNetworkInfo.userImageURL;
            // return this.socialNetworkInfo;
            // console.log(this.userImageURL)
          }
        },
        (err) => {
          console.log(err)
        }
      )
  }

  preProcessConfigurations() {
    this.statusCode = null;
    this.requestProcessing = true;
  }
}
