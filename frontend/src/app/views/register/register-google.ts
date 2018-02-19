import {SocialNetworkInfo} from './SocialNetworkInfo';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

declare const gapi: any;

export class Google{

  private appId: string;
  private elementId: string;
  private profile: any;
  private auth2: any;

  public socialNetworkInfo: SocialNetworkInfo;

  private scope = [
    'profile',
    'email',
    'https://www.googleapis.com/auth/plus.me',
    'https://www.googleapis.com/auth/contacts.readonly',
    'https://www.googleapis.com/auth/admin.directory.user.readonly'
  ].join(' ');
  public response = new BehaviorSubject<boolean>(false);

  constructor(appId: string, elementId: string) {
    this.appId = appId;
    this.elementId = elementId;

    this.init();
  }

  login() {
     // console.log(this.response)
      this.response
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
              }
              return this.socialNetworkInfo;
              // console.log(this.socialNetworkInfo)
            }
          },
          (err) => {
            console.log(err)
          }
        )
  }


  init() {
    let js;
    const id = 'google-jssdk';
    const ref = document.getElementsByTagName('script')[0];

    if (document.getElementById(id)) {
      return;
    }

    js = document.createElement('script');
    js.id = id;
    js.async = true;
    js.defer = true;
    js.src = '//apis.google.com/js/platform.js?onload=init';

    window.onload = function () {
      ref.parentNode.insertBefore(js, ref);
    };

    js.onload = results => {
      this.initSDK()
    };
    // console.log('init - 1');
  }

  initSDK() {
    gapi.load(
      'auth2',
      () => {
        this.auth2 = gapi.auth2.init({
          client_id: this.appId,
          cookiepolicy: 'single_host_origin',
          scope: this.scope
        });
        /*
        this.auth2.attachClickHandler(
          this.elementId,
          {},
          (googleUser) => {

            console.log(googleUser.getBasicProfile())
            console.log(googleUser.getAuthResponse())
          },
          (error) => {
            console.log(JSON.stringify(error, undefined, 2));
          })
          */
       // console.log(this.auth2)
        this.setCallback();

      }
    );
  }

  setCallback() {

    this.auth2.attachClickHandler(
      this.elementId,
      {},
      (response) => {
        // this.googleUser = googleUser;
        this.response.next(response);
        // console.log(response);
        // console.log(this.response);
        /*
        this.profile = googleUser.getBasicProfile();

        this.socialNetworkInfo = {
          socialNetwork: 'google',
          userName: this.profile.getName(),
          userEmail: this.profile.getEmail(),
          userToken: googleUser.getAuthResponse().id_token,
          userId: this.profile.getId(),
          userImageURL: this.profile.getImageUrl(),
          expiresIn: '',
          signedRequest: ''
        };
        */

        // return this.socialNetworkInfo
        // console.log('Google Token Id - ' + googleUser.getAuthResponse().id_token);
        // console.log(this.socialNetworkInfo)
        // return this.socialNetworkInfo
      },
      (error) => {
        console.log(JSON.stringify(error, undefined, 2));
      });

  }

}
