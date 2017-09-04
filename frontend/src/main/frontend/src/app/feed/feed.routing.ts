
import {RouterModule, Routes} from "@angular/router";
import {FeedComponent} from "./feed.component";
import {ModuleWithProviders} from "@angular/core";
import {RecommendedGroupsResolve} from "../shared/resolvers/recommended.groups.resolve";
import {UserGroupsResolve} from "../shared/resolvers/user.groups.resolve";
import {FavoriteUsersResolve} from "../shared/resolvers/favorite.users.resolve";
const routes:Routes = [
  {
    path: '',
    component: FeedComponent,
    resolve: {
      'recommendedGroups': RecommendedGroupsResolve,
      'userGroups': UserGroupsResolve,
      'favoriteContacts': FavoriteUsersResolve
    }
  },
  {
    path: '**', redirectTo: '../404'
  }
];

export const routing:ModuleWithProviders = RouterModule.forChild(routes);
