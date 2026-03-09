import axios from "axios";


async function loginApi(username, password) {

    const userPassBase64 = btoa(`${username}:${password}`);

    console.log(`Base64: ${userPassBase64}`);


    const res = await axios.get(`http://localhost:8080/login`, {

        headers: { 'Authorization': `Basic ${userPassBase64}` },
    });

    return res.data.token;
}
//-----------------------------------------------------------------
async function SignUpApi(name,username,email,password,confirm,role) {
    const userPassBase64 = btoa(`${username}:${password}`);

    const res= await axios.post(`http://localhost:8080/users`, {

        name: name,
        username: username,
        email: email,
        password: password,
        confirm: confirm,
        role:"SIMPLE_USER"
    });
    return res.data;
}


function jwtPayload(token){
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    return JSON.parse(atob(base64));
}

export default{ loginApi, jwtPayload,SignUpApi }