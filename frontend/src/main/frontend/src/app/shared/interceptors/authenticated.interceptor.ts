import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {CacheService} from "ng2-cache";
import {Observable} from "rxjs/Observable";
import {Token} from "../models/token";

@Injectable()
export class AuthenticatedInterceptor implements HttpInterceptor {
  constructor(private cacheService: CacheService) {}


  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('Interceptor');
    let bearer = 'Bearer ';
    console.log(document.cookie);
    let changedReq = req.clone({
      headers: req.headers
        .set('Authorization', bearer + <Token>this.cacheService.get('token').accessToken),
      withCredentials: true
    });
    if(req.method == 'POST') {
      changedReq = changedReq.clone({
        params: req.params.set('_csrf', document.cookie.split('=')[1])
      });
    }
    console.log(changedReq.headers.get('Authorization'));
    return next.handle(changedReq);
  }
}
