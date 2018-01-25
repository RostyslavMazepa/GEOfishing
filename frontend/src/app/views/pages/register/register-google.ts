// '834859447878-vj9upm5llmp4asg6ben3b5n4tnjs3d4v.apps.googleusercontent.com'

declare const gapi: any;

export class Google {

    private appId: string;
    private elementId: string;

    private socialNetworking: string = 'google';
    private userName: string;
    private userEmail: string;
    private userToken: string;
    private userId: string;
    private userImageURL: string;
    private expiresIn: string;
    private signedRequest: string;

    getUserName(): string {
        return this.userName;
    }

    getUserEmail(): string {
        return this.userEmail;
    }

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
        js.onload = results => {
            this.initSDK()
        }
    }

    initSDK() {
        gapi.load('auth2', () => {
            this.auth2 = gapi.auth2.init({
                client_id: this.appId,
                cookiepolicy: 'single_host_origin',
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
                this.userToken = googleUser.getAuthResponse().id_token;
                console.log('ID: ' + profile.getId());
                this.userId = profile.getId();
                console.log('Name: ' + profile.getName());
                this.userName = profile.getName();
                console.log('Image URL: ' + profile.getImageUrl());
                this.userImageURL = profile.getImageUrl();
                console.log('Email: ' + profile.getEmail());
                this.userEmail = profile.getEmail();
            },
            (error) => {
                console.log(JSON.stringify(error, undefined, 2));
                // alert(JSON.stringify(error, undefined, 2));
        });
    }
}
