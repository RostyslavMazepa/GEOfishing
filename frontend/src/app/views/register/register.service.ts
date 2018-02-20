import {Injectable} from '@angular/core';
import {SocialNetworkInfo} from './SocialNetworkInfo';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Cookie} from 'ng2-cookies';
import {Router} from '@angular/router';
import {urlRegisterAuth, urlSocialAuth} from '../../../environments/environment';
import {RegisterUser} from '../home/RegisterUser';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class RegisterService {

  constructor(private router: Router,
              private http: HttpClient) {
  }

  createAccountSocial(socialNetworkInfo: SocialNetworkInfo) {

    // const urlAuth = 'http://localhost:8081/oauth/socialAuth/';

    const params = new URLSearchParams();
    params.append('socialNetwork', socialNetworkInfo.socialNetwork);
    params.append('userName', socialNetworkInfo.userName);
    params.append('userEmail', socialNetworkInfo.userEmail);
    params.append('userToken', socialNetworkInfo.userToken);
    params.append('userId', socialNetworkInfo.userId);
    params.append('userImageURL', socialNetworkInfo.userImageURL);
    params.append('expiresIn', socialNetworkInfo.expiresIn);
    params.append('signedRequest', socialNetworkInfo.signedRequest);

    const headers = new HttpHeaders(
      {
        'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
      }
    );

    this.http.post(urlSocialAuth, params.toString(), {headers: headers})
      .subscribe(
        (data) => {
          this.saveToken(data);
          console.log(data);
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            console.log('Client-side error occured.');
          } else {
            console.log('Server-side error occured.');
          }
        })
  }

  createAccount(registerUser: RegisterUser): Observable<number> {
    const headers = new HttpHeaders(
      { 'Content-Type': 'application/json' }
    );

    console.log(registerUser);

    return this.http.post(urlRegisterAuth, registerUser, {headers: headers})
      .map(this.extractData)
      .catch(this.handleError);
  }

  saveToken(token) {
    const expireDate = new Date().getTime() + (1000 * token.expires_in);
    Cookie.set('access_token', token.access_token, expireDate);
    this.router.navigate(['/home']/*, { relativeTo: this.route }*/);
  }

  private extractData(res: Response) {
    // console.error(res);
    return res;
  }

  private handleError (error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.status);
  }
}
