import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessagesComponent } from './messages.component';
import { MessageOverviewComponent } from './message-overview/message-overview.component';
import { ChatWindowComponent } from './chat-window/chat-window.component';
import { ChatListElementComponent } from './chat-list-element/chat-list-element.component';
import {routing} from "./messages.routing";
import {MdButtonModule, MdCardModule, MdIconModule, MdInputModule, MdToolbarModule} from "@angular/material";
import { ChatMessageComponent } from './chat-message/chat-message.component';
import {FormsModule} from "@angular/forms";
import { GroupChatMessageComponent } from './group-chat-message/group-chat-message.component';
import { GroupChatComponent } from './group-chat/group-chat.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MdToolbarModule,
    MdIconModule,
    MdButtonModule,
    MdCardModule,
    MdInputModule,
    routing
  ],
  declarations: [MessagesComponent, MessageOverviewComponent, ChatWindowComponent, ChatListElementComponent, ChatMessageComponent, GroupChatMessageComponent, GroupChatComponent]
})
export class MessagesModule { }
