import {Component, Input, OnInit} from '@angular/core';
import {Message} from "../../shared/models/message";
import {CacheService} from "ng2-cache";

@Component({
  selector: 'chat-message',
  templateUrl: './chat-message.component.html',
  styleUrls: ['./chat-message.component.css']
})
export class ChatMessageComponent implements OnInit {
  private _message: Message;
  private isIncoming: boolean;

  @Input()
  set message(message: Message) {
    this._message = message;
  }

  constructor(private cacheService: CacheService) { }

  ngOnInit() {
    this.isIncoming = this._message.senderUser.username !== this.cacheService.get('username');
  }

}
