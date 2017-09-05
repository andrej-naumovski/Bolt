import {Component, Input, OnInit} from '@angular/core';
import {ChatGroup} from "../../shared/models/chat.group";
import {ChatgroupService} from "../../shared/services/chatgroup-service/chatgroup.service";
import {Router} from "@angular/router";
import {CacheService} from "ng2-cache";

@Component({
  selector: 'recommended-groups',
  templateUrl: './recommended-groups.component.html',
  styleUrls: ['./recommended-groups.component.css']
})
export class RecommendedGroupsComponent implements OnInit {
  private _group: ChatGroup;
  public isSubscribed: boolean;

  @Input()
  public set group(group: ChatGroup) {
    this._group = group;
  }

  public get group() {
    return this._group;
  }

  constructor(private chatgroupService: ChatgroupService, private router: Router, private cacheService: CacheService) { }

  ngOnInit() {
    this.isSubscribed = false;
    for(let i = 0; i < this._group.users.length; i++) {
      if(this._group.users[i].username == this.cacheService.get('username')) {
        this.isSubscribed = true;
        break;
      }
    }
  }

  subscribeToGroup() {
    this.chatgroupService
      .subscribeToGroup(this._group.name)
      .subscribe(
        (response) => {
          this.isSubscribed = true;
        },
        (error) => {
          console.log(error);
        }
      );
  }

  openGroupChat() {
    this.router.navigateByUrl('messages/group/' + this._group.name);
  }

}
