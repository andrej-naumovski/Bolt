import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthComponent } from './auth.component';
import {routing} from "./auth.routing";
import { LoginComponent } from './login/login.component';
import {MdInputModule, MdIconModule, MdButtonModule} from "@angular/material";
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MdInputModule,
    MdIconModule,
    MdButtonModule,
    routing
  ],
  declarations: [AuthComponent, LoginComponent]
})
export class AuthModule { }
