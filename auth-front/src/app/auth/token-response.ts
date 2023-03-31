export class OpenIdTokenResponse {
    access_token: string;
    refresh_token: string;
    scope: string;
    id_token: string;
    token_type: string;
    expires_in: number;

    constructor(access_token: string,
                refresh_token: string,
                scope: string,
                id_token: string,
                token_type: string,
                expires_in: number) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.scope = scope;
        this.id_token = id_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
    }
}