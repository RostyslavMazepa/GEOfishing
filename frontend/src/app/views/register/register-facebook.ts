import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {SocialNetworkInfo} from './SocialNetworkInfo';

declare const FB: any;

export class Facebook {

  private appId: string;

  public response = new BehaviorSubject<boolean>(false);
  public responseUser = new BehaviorSubject<boolean>(false);

  private socialNetworkInfo: SocialNetworkInfo;

  constructor(appId: string) {
    this.appId = appId;

    this.init();
  }

  login() {
          const fields = ['id', 'name', 'email', 'picture'];
          FB.api(`/me?fields=${fields.toString()}`, (responseUser: any) => {
            this.responseUser.next(responseUser);
          });
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
}
