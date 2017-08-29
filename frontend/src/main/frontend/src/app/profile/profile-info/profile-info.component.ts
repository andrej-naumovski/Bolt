import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../shared/models/user";

@Component({
  selector: 'profile-info',
  templateUrl: './profile-info.component.html',
  styleUrls: ['./profile-info.component.css']
})
export class ProfileInfoComponent implements OnInit {
  private _user: User;

  @Input()
  set user(user: User) {
    this._user = user;
  }

  constructor() { }

  ngOnInit() {
  }

}
