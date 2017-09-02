import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {environment} from "../../../../environments/environment";

@Injectable()
export class InterestService {

  constructor(private http: HttpClient) { }

  getInterestList(): Observable<any> {
    return this.http
      .get(environment.api + "/interests/");
  }

  addInterestToUser(interestName: string): Observable<any> {
    return this.http
      .post(environment.api + "/interests/" + interestName, {});
  }

}
