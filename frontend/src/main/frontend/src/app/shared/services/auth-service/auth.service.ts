import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import Any = jasmine.Any;
import {Observable} from "rxjs/Observable";
import {environment} from "../../../../environments/environment";

@Injectable()
export class AuthService {

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<Any> {
    return this.http.post(environment.api + '/auth/login', {
      username: username,
      password: password
    });
  }
}
