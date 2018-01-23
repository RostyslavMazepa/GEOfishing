import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { DicFishType } from './DicFishType';
import { DicFishKind } from './DicFishKind';

@Injectable()
export class HomeService {

    // URLs for CRUD operations
    allDicFishTypesUrl = 'http://localhost:8080/all-dicfishtypes';
    allDicFishKindsUrl = 'http://localhost:8080/all-dicfishkinds';

    constructor(private http: Http) {
    }
    // Fetch all Fish Types
    getAllDicFishTypes(): Observable<DicFishType[]> {
        return this.http.get(this.allDicFishTypesUrl)
            .map(this.extractData)
            .catch(this.handleError);
    }

    getAllDicFishTypeByKindId(fishTypeKindId: string): Observable<DicFishType[]> {
        return this.http.get(this.allDicFishTypesUrl, {
            params: {
                id: fishTypeKindId
            }
        }).map(this.extractData).catch(this.handleError);
    }

    // Fetch all Fish Kinds
    getAllDicFishKinds(): Observable<DicFishKind[]> {
        return this.http.get(this.allDicFishKindsUrl)
            .map(this.extractData).catch(this.handleError);
    }

    private extractData(res: Response) {
        const body = res.json();
        return body;
    }

    private handleError (error: Response | any) {
        console.error(error.message || error);
        return Observable.throw(error.status);
    }
}
