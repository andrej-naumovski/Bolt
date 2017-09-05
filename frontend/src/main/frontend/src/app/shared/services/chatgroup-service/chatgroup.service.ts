import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {environment} from "../../../../environments/environment";

@Injectable()
export class ChatgroupService {

  constructor(private http: HttpClient) { }

  getByUsername(): Observable<any> {
    return this.http
      .get(environment.api + '/groups/user');
  }

  subscribeToGroup(groupName: string): Observable<any> {
    return this.http
      .post(environment.api + '/groups/subscribe/' + groupName, {});
  }

  getRecommendedGroups(): Observable<any> {
    return this.http
      .get(environment.api + '/groups/recommended');
  }

  getGroupByGroupName(name: string): Observable<any> {
    return this.http
      .get(environment.api + '/groups/' + name);
  }
}
