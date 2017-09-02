import {RouterModule, Routes} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
import {MessageOverviewComponent} from "./message-overview/message-overview.component";
import {ChatWindowComponent} from "./chat-window/chat-window.component";
import {SingleMessageListResolve} from "../shared/resolvers/single.message.list.resolve";
import {MessagesComponent} from "./messages.component";

const routes: Routes = [
  {
    path: '',
    component: MessagesComponent,
    children: [
      {
        path: 'list',
        component: MessageOverviewComponent,
        resolve: {
          'chatList': SingleMessageListResolve
        }
      },
      {
        path: ':username',
        component: ChatWindowComponent
      }
    ]
  },
  {
    path: '**', redirectTo: '../404'
  }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
