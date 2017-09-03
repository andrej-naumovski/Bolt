import {Component, OnInit} from '@angular/core';
import {Message} from "../../shared/models/message";
import {ActivatedRoute} from "@angular/router";
import {User} from "../../shared/models/user";
import {StompService} from "@stomp/ng2-stompjs";
import * as Stomp from "@stomp/stompjs";
import {CacheService} from "ng2-cache";

@Component({
  selector: 'app-chat-window',
  templateUrl: './chat-window.component.html',
  styleUrls: ['./chat-window.component.css']
})
export class ChatWindowComponent implements OnInit {
  public chatArchive: Array<Message>;
  public otherUser: User;
  public currentUser: User;
  public message: string;
  public stompClient: Stomp.Client;


  constructor(private route: ActivatedRoute,
              private stomp: StompService,
              private cacheService: CacheService) {
  }

  ngOnInit() {
    this.chatArchive = this.route.snapshot.data['chatArchive'];
    this.otherUser = this.route.snapshot.data['friendUser'];
    this.currentUser = this.route.snapshot.data['currentUser'];
    console.log(this.chatArchive);
    if (this.otherUser.photoUrl == null) {
      this.otherUser.photoUrl = '../../../assets/artist-placeholder.png';
    }
    let socket = new WebSocket('ws://localhost:8080/api/ws-connect');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, (frame) => {
      console.log('connected ' + frame);
      this.stompClient.subscribe(
        '/chat/private/' + this.cacheService.get('username') + '/' + this.otherUser.username,
        (message) => {
          console.log('Message is: ' + message);
          this.chatArchive.push(<Message>JSON.parse(message.body));
        }
      )
    });
  }

  sendMessage() {
    this.stompClient.send('/message/' + this.cacheService.get('username') + '/' + this.otherUser.username, {}, this.message);
    let newId = 1;
    if(this.chatArchive.length > 0) {
      newId = this.chatArchive[this.chatArchive.length - 1].id + 1;
    }
    let newMessage: Message = {
      id: newId,
      message: this.message,
      senderUser: this.currentUser,
      receiverUser: this.otherUser,
      timestamp: new Date()
    };
    this.chatArchive.push(newMessage);
    this.message = '';
  }

}
