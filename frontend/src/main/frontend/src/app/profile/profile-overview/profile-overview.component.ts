import { Component, OnInit } from '@angular/core';
import {User} from "../../shared/models/user";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-profile-overview',
  templateUrl: './profile-overview.component.html',
  styleUrls: ['./profile-overview.component.css']
})
export class ProfileOverviewComponent implements OnInit {
  private profile: User;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.profile = this.route.snapshot.data['profile'];
  }

}
