import {Injectable} from "@angular/core";
import {User} from "../models/user";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {CacheService} from "ng2-cache";
import {ProfileService} from "../services/profile-service/profile.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class CurrentUserResolve implements Resolve<User> {
  constructor(
    private cacheService: CacheService,
    private profileService: ProfileService
  ) {

  }


  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User> | Promise<User> | User {
    return this.profileService.getProfileByUsername(this.cacheService.get('username'));
  }
}
