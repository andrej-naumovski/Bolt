import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {environment} from "../../../../environments/environment";

@Injectable()
export class FriendshipService {

  constructor(private http: HttpClient) { }

  hasSentFriendRequest(username: string): Observable<any> {
    return this.http
      .get(environment.api + '/users/requests/sent/' + username);
  }

  hasReceivedFriendRequest(username: string): Observable<any> {
    return this.http
      .get(environment.api + '/users/requests/received/' + username);
  }

}
