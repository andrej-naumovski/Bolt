import {Component, OnInit} from '@angular/core';
import {CacheService} from "ng2-cache";
import {AuthService} from "../shared/services/auth-service/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
  public menu: Array<any>;

  constructor(private cacheService: CacheService,
              private authService: AuthService,
              private router: Router) {
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
