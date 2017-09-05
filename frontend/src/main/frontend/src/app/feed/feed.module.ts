import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FeedComponent } from './feed.component';
import {routing} from "./feed.routing";
import {FormsModule} from "@angular/forms";
import {
  MdButtonModule, MdCardModule, MdGridListModule, MdIconModule, MdSidenavModule,
  MdToolbarModule
} from "@angular/material";
import { FavoriteUserComponent } from './favorite-user/favorite-user.component';
import { YourGroupsComponent } from './your-groups/your-groups.component';
import { RecommendedGroupsComponent } from './recommended-groups/recommended-groups.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MdToolbarModule,
    MdButtonModule,
    MdIconModule,
    MdSidenavModule,
    MdCardModule,
    MdGridListModule,
    routing
  ],
  declarations: [FeedComponent, FavoriteUserComponent, YourGroupsComponent, RecommendedGroupsComponent]
})
export class FeedModule { }
