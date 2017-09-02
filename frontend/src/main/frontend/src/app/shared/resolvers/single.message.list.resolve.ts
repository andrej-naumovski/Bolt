import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {User} from "../models/user";
import {MessageService} from "../services/message-service/message.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class SingleMessageListResolve implements Resolve<Array<User>> {
  constructor(private messageService: MessageService) {}


  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<User>> | Promise<Array<User>> | Array<User> {
    return this.messageService.getSingleMessagesList();
  }
}
