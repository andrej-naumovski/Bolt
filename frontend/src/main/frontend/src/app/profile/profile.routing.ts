import {RouterModule, Routes} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
import {ProfileOverviewComponent} from "./profile-overview/profile-overview.component";
import {UserResolve} from "../shared/resolvers/user.resolve";
import {SentRequestResolve} from "../shared/resolvers/sent.request.resolve";
import {ReceivedRequestResolve} from "../shared/resolvers/received.request.resolve";
import {FriendshipStatusResolve} from "../shared/resolvers/friendship.status.resolve";
import {InterestResolve} from "../shared/resolvers/interest.resolve";

const routes:Routes = [
  {
    path: ':username',
    component: ProfileOverviewComponent,
    resolve: {
      'profile': UserResolve,
      'hasSentRequest': SentRequestResolve,
      'hasReceivedRequest': ReceivedRequestResolve,
      'isFriendsWith': FriendshipStatusResolve,
      'interestList': InterestResolve
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
