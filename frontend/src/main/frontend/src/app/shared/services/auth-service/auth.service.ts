import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {environment} from "../../../../environments/environment";
import {Headers, Http, URLSearchParams} from "@angular/http";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw'
import {RegisterUser} from "../../models/register.user";
import {CacheService} from "ng2-cache";

@Injectable()
export class AuthService {

  constructor(private http: Http, private cacheService: CacheService) {
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

  isLoggedIn(): boolean {
    return this.cacheService.get('token');
  }

  requestPasswordResetToken(username: string): Observable<any> {
    return this.http
      .get(environment.api + '/auth/reset/' + username)
      .map((response) => {
        console.log(response);
        return response.json();
      })
      .catch((error) => {
        console.log(error);
        return Observable.throw(error || 'Server error');
      });
  }

  validatePasswordResetToken(token: string): Observable<any> {
    return this.http
      .get(environment.api + '/auth/reset/validate?token=' + token)
      .map((response) => {
        console.log(response);
        return response.json();
      })
      .catch((error) => {
        console.log(error);
        return Observable.throw(error || 'Server error')
      });
  }

  resetPassword(token: string, password: string): Observable<any> {
    let params = new URLSearchParams();
    params.append('token', token);
    params.append('password', password);
    return this.http
      .post(environment.api + '/auth/reset', params)
      .map((response) => {
        console.log(response);
        return response.json();
      })
      .catch((error) => {
        console.log(error);
        return Observable.throw(error || 'Server error');
      });
  }
}
