import {Injectable} from "@angular/core";
import {GroupChatMessage} from "../models/group.chat.message";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {MessageService} from "../services/message-service/message.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class GroupChatArchiveResolve implements Resolve<Array<GroupChatMessage>> {
  constructor(private messageService: MessageService) {

  }



  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<GroupChatMessage>> | Promise<Array<GroupChatMessage>> | Array<GroupChatMessage> {
    return this.messageService.getPastGroupMessages(route.paramMap.get('groupName'));
  }
}
