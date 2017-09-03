import {Component, OnInit} from '@angular/core';
import {User} from "../../shared/models/user";
import {ActivatedRoute, Router} from "@angular/router";
import {CacheService} from "ng2-cache";
import {ProfileService} from "../../shared/services/profile-service/profile.service";
import {GenericResponse} from "../../shared/models/generic.response";
import {FriendshipService} from "../../shared/services/friendship-service/friendship.service";
import {AuthService} from "../../shared/services/auth-service/auth.service";

@Component({
  selector: 'app-profile-overview',
  templateUrl: './profile-overview.component.html',
  styleUrls: ['./profile-overview.component.css']
})
export class ProfileOverviewComponent implements OnInit {
  public profile: User;
  public isCurrentUser: boolean;
  public isFriendsWith: boolean;
  public isFriendRequestSent: boolean;
  public isFriendRequestReceived: boolean;
  public successMessage: string;
  public errorMessage: string;

  constructor(private route: ActivatedRoute,
              private cacheService: CacheService,
              private profileService: ProfileService,
              private friendshipService: FriendshipService,
              private authService: AuthService,
              private router: Router) {
  }

  ngOnInit() {
    this.profile = this.route.snapshot.data['profile'];
    console.log(this.route.snapshot.paramMap.get('username'));
    this.isCurrentUser = this.route.snapshot.paramMap.get('username') === this.cacheService.get('username');
    this.isFriendRequestSent = (<GenericResponse<boolean>>this.route.snapshot.data['hasSentRequest']).message;
    this.isFriendRequestReceived = (<GenericResponse<boolean>>this.route.snapshot.data['hasReceivedRequest']).message;
    this.isFriendsWith = (<GenericResponse<boolean>>this.route.snapshot.data['isFriendsWith']).message;
  }

  sendMessage() {
    this.router.navigateByUrl('/messages/chat/' + this.route.snapshot.paramMap.get('username'));
  }

  sendFriendRequest() {
    let friendUsername = this.route.snapshot.paramMap.get('username');
    this.profileService
      .sendFriendRequest(friendUsername)
      .subscribe(
        (response) => {
          console.log(response);
          this.isFriendRequestSent = true;
        },
        (error) => {
          let err = <GenericResponse<string>> error;
          console.log(err);
        }
      );
  }

  acceptFriendRequest() {
    let friendUsername = this.route.snapshot.paramMap.get('username');
    this.friendshipService
      .acceptFriendRequest(friendUsername)
      .subscribe(
        (response) => {
          let res = <GenericResponse<string>> response;
          this.successMessage = res.message;
          this.errorMessage = null;
        },
        (error) => {
          let err = <GenericResponse<string>> error;
          this.errorMessage = err.message;
          this.successMessage = null;
        }
      );
  }

  declineFriendRequest() {
    let friendUsername = this.route.snapshot.paramMap.get('username');
    this.friendshipService
      .declineFriendRequest(friendUsername)
      .subscribe(
        (response) => {
          let res = <GenericResponse<string>> response;
          this.successMessage = res.message;
          this.errorMessage = null;
        },
        (error) => {
          let err = <GenericResponse<string>> error;
          this.errorMessage = err.message;
          this.successMessage = null;
        }
      );
  }

  deleteFriend() {
    let friendUsername = this.route.snapshot.paramMap.get('username');
    this.friendshipService
      .deleteFriend(friendUsername)
      .subscribe(
        (response) => {
          this.successMessage = "Successfully deleted friend";
          this.errorMessage = null;
        },
        (error) => {
          let err = <GenericResponse<string>> error;
          this.errorMessage = err.message;
          this.successMessage = null;
        }
      )
  }

  logout() {
    this.authService
      .logout()
      .subscribe(
        (response) => {
          console.log(response);
          this.router.navigateByUrl('/auth/login');
        },
        (error) => {
          console.log(error);
        }
      )
  }
}
