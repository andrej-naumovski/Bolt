import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../shared/models/user";
import {Router} from "@angular/router";

@Component({
  selector: 'chat-list-element',
  templateUrl: './chat-list-element.component.html',
  styleUrls: ['./chat-list-element.component.css']
})
export class ChatListElementComponent implements OnInit {
  private _user: User;

  @Input()
  set user(user: User) {
    this._user = user;
  }

  get user() {
    return this._user;
  }

  constructor(private router: Router) { }

  ngOnInit() {
    if(this._user.photoUrl == null) {
      this._user.photoUrl = '../../../assets/artist-placeholder.png';
    }
  }

  openChat() {
    this.router.navigateByUrl('/messages/chat/' + this._user.username);
  }

}
