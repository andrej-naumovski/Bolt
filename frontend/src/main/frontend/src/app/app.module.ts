import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { NotFoundComponent } from './shared/components/not-found/not-found.component';
import {routing} from "./app.routing";
import {AuthModule} from "./auth/auth.module";
import {MdButtonModule, MdDialogModule, MdIconModule, MdInputModule, MdProgressSpinnerModule} from "@angular/material";
import {AuthService} from "./shared/services/auth-service/auth.service";
import {HttpClientModule} from "@angular/common/http";
import { LoadingDialogComponent } from './shared/components/loading-dialog/loading-dialog.component';
import {CacheService} from "ng2-cache";

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    LoadingDialogComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    HttpModule,
    BrowserAnimationsModule,
    MdInputModule,
    MdButtonModule,
    MdDialogModule,
    MdProgressSpinnerModule,
    MdIconModule,
    AuthModule,
    routing
  ],
  providers: [AuthService, CacheService],
  bootstrap: [AppComponent],
  entryComponents: [LoadingDialogComponent]
})
export class AppModule { }
