import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {environment} from "../../../../environments/environment";

@Injectable()
export class MessageService {

  constructor(private http: HttpClient) { }

  getSingleMessagesList(): Observable<any> {
    return this.http
      .get(environment.api + '/messages/last');
  }

  getPastMessagesWithUser(username: string): Observable<any> {
    return this.http
      .get(environment.api + '/messages/chat/' + username);
  }

}
