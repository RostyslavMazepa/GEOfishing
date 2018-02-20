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
              };
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

    console.log(ref)

    if (document.getElementById(id)) {
      return;
    }
    console.log(document.getElementById(id))

    js = document.createElement('script');
    js.id = id;
    js.async = true;
    js.defer = true;
    js.src = '//apis.google.com/js/platform.js?onload=init';

    ref.parentNode.insertBefore(js, ref);

    js.onload = results => {
      this.initSDK()
    };
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
        this.setCallback();
      }
    );
  }

  setCallback() {
    this.auth2.attachClickHandler(
      this.elementId,
      {},
      (response) => {
        this.response.next(response);
      },
      (error) => {
        console.log(JSON.stringify(error, undefined, 2));
      });

  }

}
