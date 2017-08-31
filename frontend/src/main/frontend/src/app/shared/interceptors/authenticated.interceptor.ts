import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {CacheService} from "ng2-cache";
import {Observable} from "rxjs/Observable";
import {Token} from "../models/token";

@Injectable()
export class AuthenticatedInterceptor implements HttpInterceptor {
  constructor(private cacheService: CacheService) {}


  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('Intercepting here');
    const changedReq = req.clone({
      headers: req.headers.set('Authorization', 'Bearer ' + <Token>this.cacheService.get('token').accessToken)
    });
    return next.handle(changedReq);
  }
}
