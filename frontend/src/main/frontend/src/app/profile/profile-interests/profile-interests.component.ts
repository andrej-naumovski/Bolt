import {Component, Input, OnInit} from '@angular/core';
import {Interest} from "../../shared/models/interest";
import {ActivatedRoute} from "@angular/router";
import {InterestService} from "../../shared/services/interest-service/interest.service";
import {GenericResponse} from "../../shared/models/generic.response";

@Component({
  selector: 'profile-interests',
  templateUrl: './profile-interests.component.html',
  styleUrls: ['./profile-interests.component.css']
})
export class ProfileInterestsComponent implements OnInit {
  private _interests: Array<Interest>;
  private _isCurrentUser: boolean;
  private interestList: Array<Interest>;
  private isInEditMode: boolean;
  private errorMessage: string;
  public selectedInterest: Interest;

  @Input()
  set interests(interests: Array<Interest>) {
    this._interests = interests || [];
  }

  @Input()
  set currentUser(currentUser: boolean) {
    this._isCurrentUser = currentUser;
  }

  constructor(private route: ActivatedRoute, private interestService: InterestService) { }

  ngOnInit() {
    this.isInEditMode = false;
    this.interestList = this.route.snapshot.data['interestList'];
  }

  toggleEditMode() {
    if(!this.isInEditMode) {
      this.errorMessage = null;
    }
    this.isInEditMode = !this.isInEditMode;
  }

  saveInterest() {
    this.interestService
      .addInterestToUser(this.selectedInterest.name)
      .subscribe(
        (response) => {
          console.log(response);
          let responseInterest = <Interest> response;
          this._interests.push(responseInterest);
          this.errorMessage = null;
        },
        (error) => {
          let err = <GenericResponse<string>> error.error;
          this.errorMessage = err.message;
        }
      );
    this.toggleEditMode();
  }

}
