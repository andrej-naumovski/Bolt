import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthComponent } from './auth.component';
import {routing} from "./auth.routing";
import { LoginComponent } from './login/login.component';
import {MdInputModule, MdIconModule, MdButtonModule} from "@angular/material";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MdInputModule,
    MdIconModule,
    MdButtonModule,
    ReactiveFormsModule,
    routing
  ],
  declarations: [AuthComponent, LoginComponent]
})
export class AuthModule { }
