
import {RouterModule, Routes} from "@angular/router";
import {FeedComponent} from "./feed.component";
import {ModuleWithProviders} from "@angular/core";
const routes:Routes = [
  {
    path: '',
    component: FeedComponent,
  },
  {
    path: '**', redirectTo: '../404'
  }
];

export const routing:ModuleWithProviders = RouterModule.forChild(routes);
