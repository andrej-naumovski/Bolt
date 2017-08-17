import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../shared/services/auth-service/auth.service";
import {MdDialog} from "@angular/material";
import {LoadingDialogComponent} from "../../shared/components/loading-dialog/loading-dialog.component";
import {Router} from "@angular/router";
import {CacheService} from "ng2-cache";

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
    private cacheService: CacheService
  ) {
    this.loginForm = formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.compose([Validators.required, Validators.minLength(6)])]
    })
  }

  ngOnInit() {
    this.loginFailed = false;
  }

  onSubmit() {
    let dialogRef = this.dialog.open(LoadingDialogComponent);
    this.authService
      .login(this.loginForm.controls.username.value, this.loginForm.controls.password.value)
      .subscribe(
        (response) => {
          dialogRef.close();
          this.router.navigateByUrl('feed');
          if(this.cacheService.get('token')) {
            this.cacheService.remove('token');
          }
          this.cacheService.set('token', response);
        },
        (error) => {
          if(error.status === 401) {
            this.loginFailed = true;
          }
          dialogRef.close();
        })
  }

}
