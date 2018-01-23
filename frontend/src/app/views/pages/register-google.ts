// '834859447878-vj9upm5llmp4asg6ben3b5n4tnjs3d4v.apps.googleusercontent.com'

declare const gapi: any;

export class Google {
    private appId: string;
    private elementId: string;

    private scope = [
        'profile',
        'email',
        'https://www.googleapis.com/auth/plus.me',
        'https://www.googleapis.com/auth/contacts.readonly',
        'https://www.googleapis.com/auth/admin.directory.user.readonly'
    ].join(' ');

    public auth2: any;

    constructor(appId: string, elementId: string) {
        this.appId = appId;
        this.elementId = elementId;

        this.init();
    }

    login() {
        // gapi.login()
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
        // console.log(ref.parentNode.insertBefore(js, ref));
        js.onload = results => {
            this.initSDK()
        }
    }

    initSDK() {
        gapi.load('auth2', () => {
            this.auth2 = gapi.auth2.init({
                client_id: this.appId,
                // client_secret: 'XD2DUg0b1hnvohJPo2UYiKOW',
                // redirect_url: 'http://localhost:4200/#/pages/register',
                // discoveryDocs: ['https://people.googleapis.com/$discovery/rest?version=v1'],
                cookiepolicy: 'single_host_origin', // 'http://localhost:4200/#/pages/register',
                scope: this.scope
            });
            // this.attachSignin(this.element.nativeElement.firstChild);
            this.setCallback(document.getElementById(this.elementId))
        });
    }

    setCallback(element) {
        this.auth2.attachClickHandler(element, {},
            (googleUser) => {
                const profile = googleUser.getBasicProfile();
                console.log('Token || ' + googleUser.getAuthResponse().id_token);
                console.log('ID: ' + profile.getId());
                console.log('Name: ' + profile.getName());
                console.log('Image URL: ' + profile.getImageUrl());
                console.log('Email: ' + profile.getEmail());
            },
            (error) => {
                console.log(JSON.stringify(error, undefined, 2));
                // alert(JSON.stringify(error, undefined, 2));
        });
    }
}
