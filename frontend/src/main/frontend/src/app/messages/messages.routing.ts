import {RouterModule, Routes} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
import {MessageOverviewComponent} from "./message-overview/message-overview.component";
import {ChatWindowComponent} from "./chat-window/chat-window.component";
import {SingleMessageListResolve} from "../shared/resolvers/single.message.list.resolve";
import {MessagesComponent} from "./messages.component";
import {ChatArchiveResolve} from "../shared/resolvers/chat.archive.resolve";
import {UserResolve} from "../shared/resolvers/user.resolve";

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
        path: 'chat/:username',
        component: ChatWindowComponent,
        resolve: {
          'chatArchive': ChatArchiveResolve,
          'friendUser': UserResolve
        }
      }
    ]
  },
  {
    path: '**', redirectTo: '../404'
  }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
