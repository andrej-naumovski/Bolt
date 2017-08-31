import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../shared/services/auth-service/auth.service";
import {MdDialog} from "@angular/material";
import {LoadingDialogComponent} from "../../shared/components/loading-dialog/loading-dialog.component";
import {Router} from "@angular/router";
import {CacheService} from "ng2-cache";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loginFailed: boolean;

  constructor(
    formBuilder: FormBuilder,
    private authService: AuthService,
    private dialog: MdDialog,
    private router: Router,
    private cacheService: CacheService,
    private cookieService: CookieService
  ) {
    this.loginForm = formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.compose([Validators.required, Validators.minLength(6)])]
    })
  }

  ngOnInit() {
    this.loginFailed = false;
    if(this.cacheService.get('token')) {
      this.router.navigateByUrl('profile/' + this.cacheService.get('username'));
    }
  }

  onSubmit() {
    let dialogRef = this.dialog.open(LoadingDialogComponent);
    this.authService
      .login(this.loginForm.controls.username.value, this.loginForm.controls.password.value)
      .subscribe(
        (response) => {
          dialogRef.close();
          if(this.cacheService.get('token')) {
            this.cacheService.remove('token');
            this.cacheService.remove('username');
          }
          this.cacheService.set('token', response);
          this.cacheService.set('username', this.loginForm.controls.username.value);
          console.log(this.cookieService.get('XSRF-TOKEN'));
          this.router.navigateByUrl('profile/' + this.loginForm.controls.username.value);
        },
        (error) => {
          if(error.status === 401) {
            this.loginFailed = true;
          }
          dialogRef.close();
        })
  }

}
