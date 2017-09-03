import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../shared/models/user";

@Component({
  selector: 'profile-info',
  templateUrl: './profile-info.component.html',
  styleUrls: ['./profile-info.component.css']
})
export class ProfileInfoComponent implements OnInit {
  private _user: User;
  private _isCurrentUser: boolean;

  @Input()
  set user(user: User) {
    this._user = user;
  }

  get user() {
    return this._user;
  }

  @Input()
  set currentUser(currentUser: boolean) {
    this._isCurrentUser = currentUser;
  }

  get currentUser() {
    return this._isCurrentUser;
  }

  constructor() { }

  ngOnInit() {
  }

}
