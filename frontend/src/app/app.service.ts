import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {clientId, clientSecret, urlToken} from '../environments/environment';
import {Router} from '@angular/router';
import {Cookie} from 'ng2-cookies';

@Injectable()
export class AppService {

  constructor(private router: Router,
              private http: HttpClient) {
  }

  obtainAccessToken(userName, userPassword) {
    // отримати токен доступу з обліковими даними користувача

    const params = new URLSearchParams();
    params.append('username', userName);
    params.append('password', userPassword);
    params.append('grant_type', 'password');

    const headers = new HttpHeaders(
      {
        'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
        'Authorization': 'Basic ' + btoa(clientId + ':' + clientSecret),
      }
    );

    this.http.post(urlToken, params.toString(), {headers: headers})
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

  saveToken(token) {
    const expireDate = new Date().getTime() + (1000 * token.expires_in);
    Cookie.set('access_token', token.access_token, expireDate);
    this.router.navigate(['/home']/*, { relativeTo: this.route }*/);
  }

  checkCredentials() {
    if (!Cookie.check('access_token')) {
      this.router.navigate(['/login']/*, { relativeTo: this.route }*/);
    }
  }

  logout() {
    // console.log('delete access_token')
    Cookie.delete('access_token');
    this.router.navigate(['/login']/*, { relativeTo: this.route }*/);
    // console.log(this.router)
  }

}
