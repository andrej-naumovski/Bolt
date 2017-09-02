import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {GenericResponse} from "../models/generic.response";
import {FriendshipService} from "../services/friendship-service/friendship.service";
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";

@Injectable()
export class ReceivedRequestResolve implements Resolve<GenericResponse<boolean>> {
  constructor(private friendshipService: FriendshipService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<GenericResponse<boolean>> | Promise<GenericResponse<boolean>> | GenericResponse<boolean> {
    return this.friendshipService.hasReceivedFriendRequest(route.paramMap.get('username'));
  }
}
