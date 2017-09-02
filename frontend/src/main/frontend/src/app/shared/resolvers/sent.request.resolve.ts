import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {GenericResponse} from "../models/generic.response";
import {Observable} from "rxjs/Observable";
import {FriendshipService} from "../services/friendship-service/friendship.service";
import {Injectable} from "@angular/core";

@Injectable()
export class SentRequestResolve implements Resolve<GenericResponse<boolean>> {
  constructor(private friendshipService: FriendshipService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<GenericResponse<boolean>> | Promise<GenericResponse<boolean>> | GenericResponse<boolean> {
    return this.friendshipService.hasSentFriendRequest(route.paramMap.get('username'));
  }
}
