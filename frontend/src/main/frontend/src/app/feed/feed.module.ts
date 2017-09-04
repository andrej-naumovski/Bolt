import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FeedComponent } from './feed.component';
import {routing} from "./feed.routing";
import {FormsModule} from "@angular/forms";
import {MdButtonModule, MdIconModule, MdSidenavModule, MdToolbarModule} from "@angular/material";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MdToolbarModule,
    MdButtonModule,
    MdIconModule,
    MdSidenavModule,
    routing
  ],
  declarations: [FeedComponent]
})
export class FeedModule { }
