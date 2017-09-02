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
import {CookieService} from "ngx-cookie-service";
import {FriendshipService} from "./shared/services/friendship-service/friendship.service";
import {SentRequestResolve} from "./shared/resolvers/sent.request.resolve";
import {ReceivedRequestResolve} from "./shared/resolvers/received.request.resolve";
import {FriendshipStatusResolve} from "./shared/resolvers/friendship.status.resolve";
import {InterestService} from "./shared/services/interest-service/interest.service";
import {InterestResolve} from "./shared/resolvers/interest.resolve";

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
    AuthModule,
    routing
  ],
  providers: [AuthService, CacheService, CookieService, ProfileService, UserResolve, AuthGuard,
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
    InterestResolve
  ],
  bootstrap: [AppComponent],
  entryComponents: [LoadingDialogComponent]
})
export class AppModule {
}
