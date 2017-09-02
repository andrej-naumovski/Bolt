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

  isFriendsWith(username: string): Observable<any> {
    return this.http
      .get(environment.api + '/friends/' + username + '/check');
  }

  acceptFriendRequest(username: string): Observable<any> {
    return this.http
      .post(environment.api + '/friends/accept/' + username, {});
  }

  declineFriendRequest(username: string): Observable<any> {
    return this.http
      .post(environment.api + '/friends/decline/' + username, {});
  }

  deleteFriend(username: string): Observable<any> {
    return this.http
      .delete(environment.api + '/friends/delete/' + username);
  }
}
