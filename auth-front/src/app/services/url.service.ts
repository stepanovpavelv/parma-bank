import { Injectable } from '@angular/core';
import redirectUrl from '../constants/redirect';
import tokenUrl from '../constants/token';
import { CodeHash } from './code-hash';
import { CodeVerifierService } from './code-verifier.service';

@Injectable({
  providedIn: 'root'
})
export class UrlService {
  private CODE_VERIFY: string = 'code_verify'; 

  constructor(private readonly verifier: CodeVerifierService) { }

  public async getLoginUrl() : Promise<string> {
    let hashes: CodeHash = await this.verifier.generateRandomHashes();
    sessionStorage.setItem(this.CODE_VERIFY, hashes.code_verifier);
    return redirectUrl(hashes.code_challenge);
  }

  public getTokenUrl(authCode: string) : string {
    if (!sessionStorage.getItem(this.CODE_VERIFY)) {
      throw Error('Отсутствует код верификации');
    }
    let verifyCode: string = sessionStorage.getItem(this.CODE_VERIFY)!;
    sessionStorage.removeItem(this.CODE_VERIFY);
    return tokenUrl(authCode, verifyCode);
  }
}