import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule, XSRFStrategy} from '@angular/http';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NotFoundComponent} from './shared/components/not-found/not-found.component';
import {routing} from "./app.routing";
import {AuthModule} from "./auth/auth.module";
import {
  MdButtonModule, MdDialogModule, MdIconModule, MdInputModule, MdProgressSpinnerModule,
  MdSelectModule
} from "@angular/material";
import {AuthService} from "./shared/services/auth-service/auth.service";
import {LoadingDialogComponent} from './shared/components/loading-dialog/loading-dialog.component';
import {CacheService} from "ng2-cache";
import {ProfileService} from "./shared/services/profile-service/profile.service";
import {UserResolve} from "./shared/resolvers/user.resolve";
import {AuthGuard} from "./shared/guards/auth.guard";
import {AuthenticatedInterceptor} from "./shared/interceptors/authenticated.interceptor";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FriendshipService} from "./shared/services/friendship-service/friendship.service";
import {SentRequestResolve} from "./shared/resolvers/sent.request.resolve";
import {ReceivedRequestResolve} from "./shared/resolvers/received.request.resolve";
import {FriendshipStatusResolve} from "./shared/resolvers/friendship.status.resolve";
import {InterestService} from "./shared/services/interest-service/interest.service";
import {InterestResolve} from "./shared/resolvers/interest.resolve";
import {ProfileModule} from "./profile/profile.module";
import {MessagesModule} from "./messages/messages.module";
import {MessageService} from "./shared/services/message-service/message.service";
import {SingleMessageListResolve} from "./shared/resolvers/single.message.list.resolve";
import {ChatArchiveResolve} from "./shared/resolvers/chat.archive.resolve";
import {StompConfig, StompService} from "@stomp/ng2-stompjs";
import {CurrentUserResolve} from "./shared/resolvers/current.user.resolve";
import {FeedModule} from "./feed/feed.module";
import {ChatgroupService} from "./shared/services/chatgroup-service/chatgroup.service";
import {UserGroupsResolve} from "./shared/resolvers/user.groups.resolve";
import {RecommendedGroupsResolve} from "./shared/resolvers/recommended.groups.resolve";
import {FavoriteUsersResolve} from "./shared/resolvers/favorite.users.resolve";
import {CurrentGroupResolve} from "./shared/resolvers/current.group.resolve";
import {GroupChatArchiveResolve} from "./shared/resolvers/group.chat.archive.resolve";

const stompConfig: StompConfig = {
  url: 'ws://localhost:8080/ws-connect',
  headers: {

  },
  heartbeat_in: 0,
  heartbeat_out: 20000,
  reconnect_delay: 5000,
  debug: true
};

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    LoadingDialogComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MdInputModule,
    MdButtonModule,
    MdDialogModule,
    MdProgressSpinnerModule,
    MdIconModule,
    routing
  ],
  providers: [AuthService, CacheService, ProfileService, UserResolve, AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticatedInterceptor,
      multi: true
    },
    FriendshipService,
    SentRequestResolve,
    ReceivedRequestResolve,
    FriendshipStatusResolve,
    InterestService,
    InterestResolve,
    MessageService,
    SingleMessageListResolve,
    ChatArchiveResolve,
    StompService,
    CurrentUserResolve,
    {
      provide: StompConfig,
      useValue: stompConfig
    },
    ChatgroupService,
    UserGroupsResolve,
    RecommendedGroupsResolve,
    FavoriteUsersResolve,
    CurrentGroupResolve,
    GroupChatArchiveResolve
  ],
  bootstrap: [AppComponent],
  entryComponents: [LoadingDialogComponent]
})
export class AppModule {
}
