import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {environment} from "../../../../environments/environment";
import {Headers, Http, URLSearchParams} from "@angular/http";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw'
import {RegisterUser} from "../../models/register.user";

@Injectable()
export class AuthService {

  constructor(private http: Http) {
  }

  login(username: string, password: string): Observable<any> {
    let body = new URLSearchParams();
    body.set('username', username);
    body.set('password', password);
    return this.http
      .post(environment.api + '/auth/login', body)
      .map((res) => {
        return res.json();
      })
      .catch((error) => {
        return Observable.throw(error || "Server error");
      });
  }

  register(user: RegisterUser): Observable<any> {
    return this.http
      .post(environment.api + '/auth/register', user)
      .map((res) => {
        console.log(res);
        return res.json();
      })
      .catch((error) => {
        console.log(error);
        return Observable.throw(error || "Server error");
      });
  }
}
