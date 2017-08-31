import { Component, OnInit } from '@angular/core';
import {User} from "../../shared/models/user";
import {ActivatedRoute} from "@angular/router";
import {CacheService} from "ng2-cache";

@Component({
  selector: 'app-profile-overview',
  templateUrl: './profile-overview.component.html',
  styleUrls: ['./profile-overview.component.css']
})
export class ProfileOverviewComponent implements OnInit {
  private profile: User;
  private isCurrentUser: boolean;

  constructor(private route: ActivatedRoute, private cacheService: CacheService) { }

  ngOnInit() {
    this.profile = this.route.snapshot.data['profile'];
    console.log(this.route.snapshot.paramMap.get('username'));
    this.isCurrentUser = this.route.snapshot.paramMap.get('username') === this.cacheService.get('username');
  }

}
