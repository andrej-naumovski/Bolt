import {RouterModule, Routes} from "@angular/router";
import {NotFoundComponent} from "./shared/components/not-found/not-found.component";
import {ModuleWithProviders} from "@angular/core";
import {AuthGuard} from "./shared/guards/auth.guard";
import {AppComponent} from "./app.component";

const routes: Routes = [
  {
    path: '',
    component: AppComponent,
    children: [
      {path: '', redirectTo: 'auth', pathMatch: 'prefix'},
      {path: '404', component: NotFoundComponent},
      {path: 'auth', loadChildren: 'app/auth/auth.module#AuthModule'},
      {
        path: 'feed',
        loadChildren: 'app/feed/feed.module#FeedModule',
        canActivate: [AuthGuard]
      },
      {
        path: 'profile',
        loadChildren: 'app/profile/profile.module#ProfileModule',
        canActivate: [AuthGuard]
      },
      {
        path: 'messages',
        loadChildren: 'app/messages/messages.module#MessagesModule',
        canActivate: [AuthGuard]
      },
      {path: '**', redirectTo: '404'}
    ]
  }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);
