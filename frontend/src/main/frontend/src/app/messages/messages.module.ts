import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessagesComponent } from './messages.component';
import { MessageOverviewComponent } from './message-overview/message-overview.component';
import { ChatWindowComponent } from './chat-window/chat-window.component';
import { ChatListElementComponent } from './chat-list-element/chat-list-element.component';
import {routing} from "./messages.routing";
import {MdButtonModule, MdCardModule, MdIconModule, MdToolbarModule} from "@angular/material";

@NgModule({
  imports: [
    CommonModule,
    MdToolbarModule,
    MdIconModule,
    MdButtonModule,
    MdCardModule,
    routing
  ],
  declarations: [MessagesComponent, MessageOverviewComponent, ChatWindowComponent, ChatListElementComponent]
})
export class MessagesModule { }
