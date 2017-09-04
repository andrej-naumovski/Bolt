import {Component, OnInit} from '@angular/core';
import {CacheService} from "ng2-cache";
import {AuthService} from "../shared/services/auth-service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
  public menu: Array<any>;

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
