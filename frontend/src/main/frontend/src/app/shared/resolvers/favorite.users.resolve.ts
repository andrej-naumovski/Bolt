import {Injectable} from "@angular/core";
import {User} from "../models/user";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {ProfileService} from "../services/profile-service/profile.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class FavoriteUsersResolve implements Resolve<Array<User>> {
  constructor(private profileService: ProfileService) {

  }


  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<User>> | Promise<Array<User>> | Array<User> {
    return this.profileService.getFavoriteContacts();
  }
}
