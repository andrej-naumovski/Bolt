import { Component, OnInit } from '@angular/core';
import {User} from "../../shared/models/user";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-message-overview',
  templateUrl: './message-overview.component.html',
  styleUrls: ['./message-overview.component.css']
})
export class MessageOverviewComponent implements OnInit {
  private chatList: Array<User>;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.chatList = this.route.snapshot.data['chatList'];
    this.chatList.push({
      id: 1,
      username: 'test',
      firstName: 'Test',
      lastName: 'Smith',
      contact: {
        email: 'test@test.com'
      },
      interests: []
    });
    console.log(this.route.parent);
  }

}
