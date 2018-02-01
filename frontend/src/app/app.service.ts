import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Cookie } from 'ng2-cookies';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { urlToken, clientId, clientSecret } from '../environments/environment';

export class Foo {
  constructor(
    public id: number,
    public name: string) { }
}

@Injectable()
export class AppService {

  constructor(
    private router: Router,
    private http: HttpClient
  ) {}

  obtainAccessToken(user) {
    // отримати токен доступу з обліковими даними користувача
    // const client_id = 'geofappid';
    // const client_secret = 'mXunCZdhCD8tRA7a';
    // const url = 'http://localhost:8081/oauth/token';
    // 134.249.121.55

    const params = new URLSearchParams();
    params.append('username', user.username);
    params.append('password', user.password);
    params.append('grant_type', 'password');
    // params.append('client_id', client_id);

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

  getResource(resourceUrl): Observable<Foo> {
    const headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      'Authorization': 'Bearer ' + Cookie.get('access_token')
    });

    return this.http.get(resourceUrl, { headers: headers })
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  checkCredentials() {
    if (!Cookie.check('access_token')) {
      this.router.navigate(['/login']);
    }
  }


  logout() {
    console.log('delete access_token')
    Cookie.delete('access_token');
    this.router.navigate(['/pages/login']);
  }
}
