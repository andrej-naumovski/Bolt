import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {ChatGroup} from "../models/chat.group";
import {ChatgroupService} from "../services/chatgroup-service/chatgroup.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class CurrentGroupResolve implements Resolve<ChatGroup> {
  constructor(private chatgroupService: ChatgroupService) {

  }


  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ChatGroup> | Promise<ChatGroup> | ChatGroup {
    return this.chatgroupService.getGroupByGroupName(route.paramMap.get('groupName'));
  }
}
