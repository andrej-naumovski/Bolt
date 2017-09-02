import {RouterModule, Routes} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
import {MessageOverviewComponent} from "./message-overview/message-overview.component";
import {ChatWindowComponent} from "./chat-window/chat-window.component";

const routes:Routes = [
  {
    path: '',
    component: MessageOverviewComponent
  },
  {
    path: ':username',
    component: ChatWindowComponent
  },
  {
    path: '**', redirectTo: '../404'
  }
];

export const routing:ModuleWithProviders = RouterModule.forChild(routes);
