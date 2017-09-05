import {Component, Input, OnInit} from '@angular/core';
import {GroupChatMessage} from "../../shared/models/group.chat.message";
import {CacheService} from "ng2-cache";

@Component({
  selector: 'group-chat-message',
  templateUrl: './group-chat-message.component.html',
  styleUrls: ['./group-chat-message.component.css']
})
export class GroupChatMessageComponent implements OnInit {
  private _message: GroupChatMessage;

  @Input()
  public set message(message: GroupChatMessage) {
    this._message = message;
  }

  public get message() {
    return this._message;
  }

  constructor(private cacheService: CacheService) { }

  ngOnInit() {
  }

}
