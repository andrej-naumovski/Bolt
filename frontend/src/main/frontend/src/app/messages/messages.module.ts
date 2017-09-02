import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessagesComponent } from './messages.component';
import { MessageOverviewComponent } from './message-overview/message-overview.component';
import { ChatWindowComponent } from './chat-window/chat-window.component';
import { ChatListElementComponent } from './chat-list-element/chat-list-element.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [MessagesComponent, MessageOverviewComponent, ChatWindowComponent, ChatListElementComponent]
})
export class MessagesModule { }
