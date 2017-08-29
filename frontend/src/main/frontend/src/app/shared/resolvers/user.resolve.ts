
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {User} from "../models/user";
import {Observable} from "rxjs/Observable";
import {ProfileService} from "../services/profile-service/profile.service";
import {Injectable} from "@angular/core";

@Injectable()
export class UserResolve implements Resolve<User> {
  constructor(private profileService: ProfileService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<User> | Promise<User> | User {
    return this.profileService.getProfileByUsername(route.paramMap.get('username'));
  }
}
