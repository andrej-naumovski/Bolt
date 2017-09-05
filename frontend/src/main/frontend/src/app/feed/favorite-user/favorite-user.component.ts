import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../shared/models/user";
import {Router} from "@angular/router";

@Component({
  selector: 'favorite-user',
  templateUrl: './favorite-user.component.html',
  styleUrls: ['./favorite-user.component.css']
})
export class FavoriteUserComponent implements OnInit {
  private _user: User;

  @Input()
  public set user(user: User) {
    this._user = user;
  }

  public get user() {
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
