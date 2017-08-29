import { Injectable } from '@angular/core';
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {environment} from "../../../../environments/environment";

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

@Injectable()
export class ProfileService {

  constructor(private http: Http) { }

  getProfileByUsername(username: string): Observable<any> {
    return this.http
      .get(environment.api + '/users/' + username)
      .map((res) => {
        console.log(res);
        return res.json();
      })
      .catch((err) => {
        console.log(err);
        return Observable.throw(err || 'Server error');
      });
  }

}
