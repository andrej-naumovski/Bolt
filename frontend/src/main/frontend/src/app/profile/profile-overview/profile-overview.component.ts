import { Component, OnInit } from '@angular/core';
import {User} from "../../shared/models/user";
import {ActivatedRoute} from "@angular/router";
import {CacheService} from "ng2-cache";
import {ProfileService} from "../../shared/services/profile-service/profile.service";
import {GenericResponse} from "../../shared/models/generic.response";

@Component({
  selector: 'app-profile-overview',
  templateUrl: './profile-overview.component.html',
  styleUrls: ['./profile-overview.component.css']
})
export class ProfileOverviewComponent implements OnInit {
  private profile: User;
  private isCurrentUser: boolean;
  private isFriendRequestSent: boolean;
  private isFriendRequestReceived: boolean;
  private buttonValue: string;

  constructor(
    private route: ActivatedRoute,
    private cacheService: CacheService,
    private profileService: ProfileService
  ) { }

  ngOnInit() {
    this.profile = this.route.snapshot.data['profile'];
    console.log(this.route.snapshot.paramMap.get('username'));
    this.isCurrentUser = this.route.snapshot.paramMap.get('username') === this.cacheService.get('username');
    this.isFriendRequestSent = (<GenericResponse<boolean>>this.route.snapshot.data['hasSentRequest']).message;
    this.isFriendRequestReceived = (<GenericResponse<boolean>>this.route.snapshot.data['hasReceivedRequest']).message;
    if(!this.isCurrentUser) {
      if (this.isFriendRequestReceived) {
        this.buttonValue = 'ACCEPT FRIEND REQUEST';
      }
      else if (this.isFriendRequestSent) {
        this.buttonValue = 'FRIEND REQUEST PENDING';
      } else {
        this.buttonValue = 'SEND FRIEND REQUEST';
      }
    } else {
      this.buttonValue = 'SEND FRIEND REQUEST';
    }
  }

  sendFriendRequest() {
    let friendUsername = this.route.snapshot.paramMap.get('username');
    this.profileService
      .sendFriendRequest(friendUsername)
      .subscribe(
        (response) => {
          console.log(response);
          this.isFriendRequestSent = true;
          this.buttonValue = 'FRIEND REQUEST SENT'
        },
        (error) => {
          let err = <GenericResponse<string>> error;
          console.log(err);
        }
      )
  }

}
