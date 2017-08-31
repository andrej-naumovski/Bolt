import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile.component';
import {routing} from "./profile.routing";
import { ProfileOverviewComponent } from './profile-overview/profile-overview.component';
import {MdButtonModule, MdCardModule, MdIconModule, MdToolbarModule} from "@angular/material";
import { ProfileInfoComponent } from './profile-info/profile-info.component';
import { ProfileInterestsComponent } from './profile-interests/profile-interests.component';

@NgModule({
  imports: [
    CommonModule,
    MdToolbarModule,
    MdButtonModule,
    MdCardModule,
    MdIconModule,
    routing
  ],
  declarations: [ProfileComponent, ProfileOverviewComponent, ProfileInfoComponent, ProfileInterestsComponent],
  providers: []
})
export class ProfileModule { }
