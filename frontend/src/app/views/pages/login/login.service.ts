import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { PagesRoutingModule } from '../pages-routing.module';
import { clientId, clientSecret, urlToken } from '../../../../environments/environment';
import { Cookie } from 'ng2-cookies';

@Injectable()
export class LoginService {

  constructor(
    private router: Router,
    private http: HttpClient
  ) { }

  obtainAccessToken(user) {
    // отримати токен доступу з обліковими даними користувача

    const params = new URLSearchParams();
    params.append('username', user.username);
    params.append('password', user.password);
    params.append('grant_type', 'password');

    const headers = new HttpHeaders(
      {
        'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
        'Authorization': 'Basic ' + btoa(clientId + ':' + clientSecret),
      }
    );

    this.http.post(urlToken, params.toString(), { headers: headers })
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
    this.router.navigate(['/']);
  }

  logout() {
    console.log('delete access_token')
    Cookie.delete('access_token');
    this.router.navigate(['../../../pages/login']);
    console.log()
  }

}
