import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Buffer } from 'buffer';
import { Observable } from 'rxjs/internal/Observable';
import { OpenIdTokenResponse } from '../auth/token-response';
import { UrlService } from './url.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private readonly http: HttpClient,
              private readonly urlServ: UrlService) { }

  getToken(authCode: string) : Observable<OpenIdTokenResponse> {
    const mockClientId = 'client';
    const mockSecret = 'secret';
    const basicAuth = 'Basic ' + Buffer.from(`${mockClientId}:${mockSecret}`).toString('base64');;
    const headers = new HttpHeaders({
      'content-type': 'application/json',
      'Authorization': basicAuth
    });
    const options = {
      headers: headers
    };
    let tokenUrl: string = this.urlServ.getTokenUrl(authCode);
    return this.http.post<OpenIdTokenResponse>(tokenUrl, null, options);
  }
}