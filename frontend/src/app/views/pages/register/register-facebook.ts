import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { SocialNetworkInfo } from "./SocialNetworkInfo";

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
            if (response.status === 'connected'){
                let userId = response.authResponse.userID;
                let userToken = response.authResponse.accessToken;
                let expiresIn = response.authResponse.expiresIn;
                let signedRequest = response.authResponse.signedRequest;

                new Promise((resolve, reject) => {
                    let fields = [
                        'id', 'name', 'email', 'picture'//, 'cover', 'birthday'
                    ];
                    FB.api(`/me?fields=${fields.toString()}`, (response: any) => {
                        resolve(response);
                        this.socialNetworkInfo = [{
                            socialNetworking: 'facebook',
                            userName: response.name,
                            userEmail: response.email||'',
                            userToken: userToken,
                            userId: userId,
                            userImageURL: response.picture.data.url,
                            expiresIn: expiresIn,
                            signedRequest: signedRequest
                        }];
                        //console.log(response)
                        //console.log(this.socialNetworkInfo)
                    });
                });
            } else if (response.status === 'not_authorized'){
                FB.login()
            } else if (response.status === 'unknown'){
                FB.logout()
            }
            console.log(response.status)
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
