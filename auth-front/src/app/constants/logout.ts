const logoutUrl = (token: string) => {
    const redirectUri = 'http://127.0.0.1:4200/home';
    return `http://localhost:8082/logout?post_logout_redirect_uri=${redirectUri}&id_token_hint=${token}`;
};

export default logoutUrl;