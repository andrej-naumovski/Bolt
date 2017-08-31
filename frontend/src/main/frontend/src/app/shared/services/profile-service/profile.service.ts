import { Injectable } from '@angular/core';
import {Observable} from "rxjs/Observable";
import {environment} from "../../../../environments/environment";

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import {HttpClient} from "@angular/common/http";
import {CacheService} from "ng2-cache";

@Injectable()
export class ProfileService {

  constructor(private http: HttpClient, private cacheService: CacheService) { }

  getProfileByUsername(username: string): Observable<any> {
    return this.http
      .get(environment.api + '/users/' + username);
  }

}
