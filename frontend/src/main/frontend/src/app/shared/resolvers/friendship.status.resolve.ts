import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {GenericResponse} from "../models/generic.response";
import {FriendshipService} from "../services/friendship-service/friendship.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class FriendshipStatusResolve implements Resolve<GenericResponse<boolean>> {
  constructor(private friendshipService: FriendshipService) {}


  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<GenericResponse<boolean>> | Promise<GenericResponse<boolean>> | GenericResponse<boolean> {
    return this.friendshipService.isFriendsWith(route.paramMap.get('username'));
  }
}
