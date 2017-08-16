import {RouterModule, Routes} from "@angular/router";
import {NotFoundComponent} from "./shared/components/not-found/not-found.component";
import {ModuleWithProviders} from "@angular/core";

const routes:Routes = [
  {path: 'auth', loadChildren: 'app/auth/auth.module#AuthModule'},
  {path: '', redirectTo: 'auth', pathMatch: 'full'},
  {path: '**', redirectTo: '404'},
  {path: '404', component: NotFoundComponent}
];

export const routing:ModuleWithProviders = RouterModule.forRoot(routes);
