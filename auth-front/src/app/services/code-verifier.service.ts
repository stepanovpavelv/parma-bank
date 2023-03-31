import { Injectable } from '@angular/core';
import { CodeHash } from './code-hash';
import { Crypto, DefaultCrypto } from './crypto-utils';

@Injectable({
  providedIn: 'root'
})
export class CodeVerifierService {
  private readonly crypto: Crypto = new DefaultCrypto();

  constructor() { }

  public async generateRandomHashes() : Promise<CodeHash> {
    let codeVerify: string = this.generateCodeVerifier();
    let codeChallenge: string = await this.generateCodeChallengeFromVerifier(codeVerify);
    return { code_challenge: codeChallenge, code_verifier: codeVerify };
  }

  private generateCodeVerifier() : string {
    return this.crypto.generateRandom(128);
  }

  private async generateCodeChallengeFromVerifier(codeVerifier: string) : Promise<string> {
    return await this.crypto.deriveChallenge(codeVerifier);
  }
}