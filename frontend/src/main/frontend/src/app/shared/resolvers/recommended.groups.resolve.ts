import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {ChatGroup} from "../models/chat.group";
import {ChatgroupService} from "../services/chatgroup-service/chatgroup.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class RecommendedGroupsResolve implements Resolve<Array<ChatGroup>> {
  constructor(private chatgroupService: ChatgroupService) {

  }


  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<ChatGroup>> | Promise<Array<ChatGroup>> | Array<ChatGroup> {
    return this.chatgroupService.getRecommendedGroups();
  }
}
