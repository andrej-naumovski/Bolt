import {RouterModule, Routes} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
import {ProfileOverviewComponent} from "./profile-overview/profile-overview.component";
import {UserResolve} from "../shared/resolvers/user.resolve";

const routes:Routes = [
  {
    path: ':username',
    component: ProfileOverviewComponent,
    resolve: {
      'profile': UserResolve
    }
  },
  {
    path: '', redirectTo: '../home', pathMatch: 'full'
  },
  {
    path: '**', redirectTo: '../404'
  }
];

export const routing:ModuleWithProviders = RouterModule.forChild(routes);
