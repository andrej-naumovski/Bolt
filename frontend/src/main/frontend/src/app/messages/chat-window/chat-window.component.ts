import { Component, OnInit } from '@angular/core';
import {Message} from "../../shared/models/message";
import {ActivatedRoute} from "@angular/router";
import {User} from "../../shared/models/user";

@Component({
  selector: 'app-chat-window',
  templateUrl: './chat-window.component.html',
  styleUrls: ['./chat-window.component.css']
})
export class ChatWindowComponent implements OnInit {
  private chatArchive: Array<Message>;
  private otherUser: User;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.chatArchive = this.route.snapshot.data['chatArchive'];
    this.otherUser = this.route.snapshot.data['friendUser'];
    if(this.otherUser.photoUrl == null) {
      this.otherUser.photoUrl = '../../../assets/artist-placeholder.png';
    }
  }

}
