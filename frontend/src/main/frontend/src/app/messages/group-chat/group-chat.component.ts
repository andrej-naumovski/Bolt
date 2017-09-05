import { Component, OnInit } from '@angular/core';
import {User} from "../../shared/models/user";
import {ActivatedRoute} from "@angular/router";
import {GroupChatMessage} from "../../shared/models/group.chat.message";
import * as Stomp from '@stomp/stompjs';
import {ChatGroup} from "../../shared/models/chat.group";

@Component({
  selector: 'app-group-chat',
  templateUrl: './group-chat.component.html',
  styleUrls: ['./group-chat.component.css']
})
export class GroupChatComponent implements OnInit {
  public currentUser: User;
  public chatArchive: Array<GroupChatMessage>;
  public message: string;
  private stompClient: Stomp.Client;
  public group: ChatGroup;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.currentUser = this.route.snapshot.data['currentUser'];
    this.chatArchive = this.route.snapshot.data['chatArchive'];
    console.log(this.chatArchive);
    this.group = this.route.snapshot.data['group'];
    let socket = new WebSocket('ws://localhost:8080/api/ws-connect');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, (frame) => {
      console.log(frame);
      this.stompClient.subscribe('/chat/group/' + this.group.name, (message) => {
        console.log('Message is: ' + message);
        this.chatArchive.push(<GroupChatMessage>JSON.parse(message.body));
      });
    });
  }

  sendMessage() {
    this.stompClient.send('/message/group/' + this.group.name + '/' + this.currentUser.username, {}, this.message);
    this.message = '';
  }


}
