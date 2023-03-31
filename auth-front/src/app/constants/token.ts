const tokenUrl = (authCode: string, verifyCode: string) => {
    //const redirectUrl = `http://127.0.0.1:4200/authorized&grant_type=authorization_code&code=${auth_code}&code_verifier=qPsH306-ZDDaOE8DFzVn05TkN3ZZoVmI_6x4LsVglQI`;
    const redirectUrl = `http://127.0.0.1:4200/authorized&grant_type=authorization_code&code=${authCode}&code_verifier=${verifyCode}`;
    return `http://localhost:8082/oauth2/token?client_id=client&redirect_uri=${redirectUrl}`;
};

export default tokenUrl;