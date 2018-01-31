// '834859447878-vj9upm5llmp4asg6ben3b5n4tnjs3d4v.apps.googleusercontent.com'
import { SocialNetworkInfo } from './SocialNetworkInfo';

declare const gapi: any;

export class Google {

    private appId: string;
    private elementId: string;
    private profile: any;

    private socialNetworkInfo: SocialNetworkInfo[];

    private auth2: any;

    private scope = [
        'profile',
        'email',
        'https://www.googleapis.com/auth/plus.me',
        'https://www.googleapis.com/auth/contacts.readonly',
        'https://www.googleapis.com/auth/admin.directory.user.readonly'
    ].join(' ');

    constructor(appId: string, elementId: string) {
        this.appId = appId;
        this.elementId = elementId;

        this.init();
        //console.log(this.socialNetworkInfo)
    }

    login() {
        //this.initSDK(
        //this.getSocialNetworkInfo()
        //this.socialNetworkInfo
            //.then(result => console.log(result))
            //.catch(error => console.log(error));

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

        ref.parentNode.insertBefore(js, ref);
        js.onload = results => {
            this.initSDK()
        }
        //console.log('init - 1');
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
            (googleUser) => {
                this.profile = googleUser.getBasicProfile();

                this.socialNetworkInfo = [{
                    socialNetworking: 'google',
                    userName: this.profile.getName(),
                    userEmail: this.profile.getEmail(),
                    userToken: googleUser.getAuthResponse().id_token,
                    userId: this.profile.getId(),
                    userImageURL: this.profile.getImageUrl(),
                    expiresIn: '',
                    signedRequest: ''
                }]
                //console.log(this.socialNetworkInfo)
            },
            (error) => {
                console.log(JSON.stringify(error, undefined, 2));
                // alert(JSON.stringify(error, undefined, 2));
        });

    }
}
