import { BehaviorSubject } from 'rxjs/BehaviorSubject';

declare const FB: any;

export class Facebook {

    private appId: string;

    public response = new BehaviorSubject<boolean>(false);
    public data = this.response.asObservable();

    private socialNetworking: string = 'facebook';
    private userName: string;
    private userEmail: string;
    private userToken: string;
    private userId: string;
    private userImageURL: string;
    private expiresIn: string;
    private signedRequest: string;


    constructor(appId: string) {
        this.appId = appId;

        this.init();
    }

    login() {
        FB.getLoginStatus(response => {
            if (response.status === 'connected'){
                this.userId = response.authResponse.userID;
                this.userToken = response.authResponse.accessToken;
                this.expiresIn = response.authResponse.expiresIn;
                this.signedRequest = response.authResponse.signedRequest;

                console.log('userId - ' + this.userId);
                console.log('accessToken - ' + this.userToken);
                console.log('expiresIn - ' + this.expiresIn);
                console.log('signedRequest - ' + this.signedRequest);
                //this.logout()
            } else if (response.status === 'not_authorized'){
                FB.login()
            } else if (response.status === 'unknown'){
                FB.logout()
            }
        });
        // FB.login()
    }

    init() {
        let js,
            id = 'facebook-jssdk',
            ref = document.getElementsByTagName('script')[0];

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
            xfbml: true,
            version: 'v2.11'
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
