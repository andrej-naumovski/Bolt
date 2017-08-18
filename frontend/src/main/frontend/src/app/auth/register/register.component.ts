import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {RegisterUser} from "../../shared/models/user";
import {PasswordValidators} from "../../shared/validators/PasswordValidators";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  private registerForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
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
  }

}
