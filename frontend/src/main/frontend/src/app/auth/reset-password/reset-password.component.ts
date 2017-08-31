import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../shared/services/auth-service/auth.service";
import {GenericResponse} from "../../shared/models/generic.response";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  isTokenSent: boolean;
  isTokenValid: boolean;
  isPasswordReset: boolean;
  username: string;
  newPassword: string;
  token: string;
  message: string;

  constructor(private route: ActivatedRoute, private authService: AuthService) { }

  ngOnInit() {
    this.isPasswordReset = false;
    this.token = this.route.snapshot.queryParamMap.get('token');
    this.isTokenSent = this.token != null;
    if(this.isTokenSent) {
      this.authService
        .validatePasswordResetToken(this.token)
        .subscribe(
          (response) => {
            let res = <GenericResponse<boolean>> response;
            this.isTokenValid = res.message;
          }
        )
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
      );
  }

  resetPassword() {
    console.log('reset password clicked');
    this.authService
      .resetPassword(this.token, this.newPassword)
      .subscribe(
        (response) => {
          let res = <GenericResponse<string>> response;
          this.message = res.message;
          this.isPasswordReset = true;
          this.isTokenSent = false;
        },
        (error) => {
          let res = <GenericResponse<string>> error;
          this.message = res.message;
          this.isPasswordReset = true;
          this.isTokenSent = false;
        }
      )
  }

}
