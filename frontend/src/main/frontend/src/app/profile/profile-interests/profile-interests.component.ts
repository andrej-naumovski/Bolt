import {Component, Input, OnInit} from '@angular/core';
import {Interest} from "../../shared/models/interest";

@Component({
  selector: 'profile-interests',
  templateUrl: './profile-interests.component.html',
  styleUrls: ['./profile-interests.component.css']
})
export class ProfileInterestsComponent implements OnInit {
  private _interests: Array<Interest>;

  @Input()
  set interests(interests: Array<Interest>) {
    this._interests = interests || [];
  }

  constructor() { }

  ngOnInit() {
  }

}
