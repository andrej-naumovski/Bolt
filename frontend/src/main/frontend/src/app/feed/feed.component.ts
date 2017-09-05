import {Component, OnInit} from '@angular/core';
import {CacheService} from "ng2-cache";
import {AuthService} from "../shared/services/auth-service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../shared/models/user";
import {ChatGroup} from "../shared/models/chat.group";

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
  public menu: Array<any>;
  public favoriteUsers: Array<User>;
  public yourGroups: Array<ChatGroup>;
  public recommendedGroups: Array<ChatGroup>;

  constructor(private cacheService: CacheService,
              private authService: AuthService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.menu = [
      {
        name: 'HOME',
        path: '/feed',
        selected: true
      },
      {
        name: 'PROFILE',
        path: '/profile/' + this.cacheService.get('username'),
        selected: false
      },
      {
        name: 'MESSAGES',
        path: '/messages',
        selected: false
      }
    ];
    console.log(this.route.snapshot.data['favoriteContacts']);
    console.log(this.route.snapshot.data['recommendedGroups']);
    console.log(this.route.snapshot.data['userGroups']);
    this.favoriteUsers = (<Array<User>>this.route.snapshot.data['favoriteContacts']).slice(0, 3);
    this.yourGroups = <Array<ChatGroup>>this.route.snapshot.data['userGroups'];
    let tempGroups: Array<ChatGroup> = <Array<ChatGroup>>this.route.snapshot.data['recommendedGroups'];
    if(tempGroups.length <= 3) {
      this.recommendedGroups = tempGroups;
    } else {
      for(let i = 0; i < 3; i++) {
        this.recommendedGroups.push(tempGroups[Math.floor((Math.random() * 100000)) % tempGroups.length]);
      }
    }
  }

  logout() {
    this.authService
      .logout()
      .subscribe(
        (response) => {
          this.router.navigateByUrl('/login');
        }
      )
  }

}
