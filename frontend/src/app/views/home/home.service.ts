import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {DicFishes} from './DicFishes';
import {DicFishesTypes} from './DicFishesTypes';
import {dicFishesUrl, dicFishesTypesUrl} from '../../../environments/environment';

@Injectable()
export class HomeService {

  constructor(private http: HttpClient) {
  }

  // Fetch Fishes
  getDicFishes(): Observable<DicFishes[]> {
    return this.http.get(dicFishesUrl)
      .map(this.extractData)
      .catch(this.handleError);
  }

  // Fetch Fishes Types
  getDicFishesTypes(): Observable<DicFishesTypes[]> {
    return this.http.get(dicFishesTypesUrl)
      .map(this.extractData)
      .catch(this.handleError);
  }

  private extractData(body: Response) {
    // const body = res.json();
    return body;
  }

  private handleError(error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.status);
  }
}
