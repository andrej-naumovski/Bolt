import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Interest} from "../models/interest";
import {InterestService} from "../services/interest-service/interest.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class InterestResolve implements Resolve<Array<Interest>> {
  constructor(private interestService: InterestService) {}


  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<Interest>> | Promise<Array<Interest>> | Array<Interest> {
    return this.interestService.getInterestList();
  }
}
