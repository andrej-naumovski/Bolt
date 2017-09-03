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

  @Input()
  set message(message: Message) {
    this._message = message;
  }

  get message() {
    return this._message;
  }

  constructor(private cacheService: CacheService) {
    console.log(this._message);
  }

  ngOnInit() {
    console.log(this._message.senderUser.username);
    console.log(this.cacheService.get('username'));
  }

}
