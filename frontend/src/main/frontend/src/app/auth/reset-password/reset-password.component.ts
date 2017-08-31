import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../shared/services/auth-service/auth.service";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  isTokenSent: boolean;
  isTokenValid: boolean;
  username: string;

  constructor(private route: ActivatedRoute, private authService: AuthService) { }

  ngOnInit() {
    this.isTokenSent = this.route.snapshot.queryParamMap.get('token') != null;
    if(this.isTokenSent) {

    }
  }

  requestPasswordResetToken() {
    console.log('clicked');
    this.authService
      .requestPasswordResetToken(this.username)
      .subscribe(
        (res) => {
          console.log(res);
        },
        (err) => {
          console.log(err);
        }
      )
  }

}
