import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {RegisterUser} from "../../shared/models/user";
import {PasswordValidators} from "../../shared/validators/PasswordValidators";
import {AuthService} from "../../shared/services/auth-service/auth.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  private registerForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService) {
    this.registerForm = formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      repeatPassword: ['', [Validators.required, Validators.minLength(6)]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      contact: formBuilder.group({
        email: ['', [Validators.required, Validators.email]]
      })
    }, {
      validator: PasswordValidators.RepeatPassword
    });
  }

  ngOnInit() {
  }

  registerUser(user: RegisterUser) {
    console.log(user);
    this.authService
      .register(user)
      .subscribe(
        (res) => {
          console.log(res);
        },
        (error) => {
          console.log(error);
        }
      );
  }

}
