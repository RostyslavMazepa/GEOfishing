import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {SocialNetworkInfo} from './SocialNetworkInfo';

declare const FB: any;

export class Facebook {

  private appId: string;

  public response = new BehaviorSubject<boolean>(false);
  public data = this.response.asObservable();

  private socialNetworkInfo: SocialNetworkInfo[];

  constructor(appId: string) {
    this.appId = appId;

    this.init();
  }

  login() {
    FB.getLoginStatus(response => {
      if (response.status === 'connected') {
        const userId = response.authResponse.userID;
        const userToken = response.authResponse.accessToken;
        const expiresIn = response.authResponse.expiresIn;
        const signedRequest = response.authResponse.signedRequest;

        new Promise((resolve, reject) => {
          const fields = [
            'id', 'name', 'email', 'picture'//, 'cover', 'birthday'
          ];
          FB.api(`/me?fields=${fields.toString()}`, (response: any) => {
            resolve(response);
            this.socialNetworkInfo = [{
              socialNetwork: 'facebook',
              userName: response.name,
              userEmail: response.email || '',
              userToken: userToken,
              userId: userId,
              userImageURL: response.picture.data.url,
              expiresIn: expiresIn,
              signedRequest: signedRequest
            }];
            // console.log(response)
            console.log('Facebook Token Id - ' + userToken)
          });
        });
      } else if (response.status === 'not_authorized') {
        FB.login()
      } else if (response.status === 'unknown') {
        FB.logout()
      }
      // console.log(response.status)
    });
    // FB.login()
  }

  init() {
    let js;
    const id = 'facebook-jssdk';
    const ref = document.getElementsByTagName('script')[0];

    if (document.getElementById(id)) {
      return;
    }

    js = document.createElement('script');
    js.id = id;
    js.async = true;
    js.src = '//connect.facebook.net/en_US/sdk.js';

    ref.parentNode.insertBefore(js, ref);

    js.onload = results => {
      this.initSDK()
    }
  }

  initSDK() {
    FB.init({
      appId: this.appId,
      status: true,
      cookie: true,
      xfbml: true,
      version: 'v2.4',
      scope: 'publish_actions'
    });
    this.setCallback()
  }

  setCallback() {
    FB.getLoginStatus(response => {
      this.response.next(response);
    });
    // console.log(this.response);
  }

  logout() {
    FB.logout(function (response) {
      // user is now logged out
    });
  }

  /*
  post

  localhost:8081/oauth/socialAuth

  {
    "socialNetwork": "facebook",
    "username": "Rodion  Iashchuk",
    "userEmail": "yashchuk@gmail.com",
    "userToken": "EAAcsnEr99UsBAO0MzZBvWdGtfkb3UiFUfpSasMbZBMeZAJpLKabsmZA5ad93E9oxxG6bGIu9r040a1xpRZCrANfBCAO8k6srisClLD7AnMrOZCFLX3dg4t4hGGm2UZB2y58mxGgKFi6iZA0rKkUE5Mfe5tj1sq5Os2aAV5IIkDvFbKPGHG7qdIr67bSGsRtpmkcZD",
    "userId": "1281501468621067",
    "userImageURL": "",
    "expiresIn": "",
    "signedRequest": ""
  }
   */
}
