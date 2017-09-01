import {RouterModule, Routes} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ResetPasswordComponent} from "./reset-password/reset-password.component";
const routes:Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'reset',
    component: ResetPasswordComponent
  },
  {
    path: '', redirectTo: 'login', pathMatch: 'full'
  },
  {
    path: '**', redirectTo: '../404'
  }
];

export const routing:ModuleWithProviders = RouterModule.forChild(routes);
