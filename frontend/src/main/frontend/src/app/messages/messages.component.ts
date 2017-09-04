import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CacheService} from "ng2-cache";

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent implements OnInit {

  constructor(private router: Router, private cacheService: CacheService, private route: ActivatedRoute) { }

  ngOnInit() {
    console.log("MESSAGES COMPONENT");
  }

  goBack() {
    this.router.navigateByUrl('/profile/' + this.cacheService.get('username'));
  }

}
