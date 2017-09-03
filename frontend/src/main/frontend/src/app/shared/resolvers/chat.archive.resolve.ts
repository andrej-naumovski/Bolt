import {Injectable} from "@angular/core";
import {Message} from "../models/message";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {MessageService} from "../services/message-service/message.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class ChatArchiveResolve implements Resolve<Array<Message>> {
  constructor(private messageService: MessageService) {
  }


  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<Message>> | Promise<Array<Message>> | Array<Message> {
    return this.messageService.getPastMessagesWithUser(route.paramMap.get('username'));
  }
}
