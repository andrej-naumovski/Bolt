import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FeedComponent } from './feed.component';
import {routing} from "./feed.routing";

@NgModule({
  imports: [
    CommonModule,
    routing
  ],
  declarations: [FeedComponent]
})
export class FeedModule { }
