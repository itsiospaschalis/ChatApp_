import React, {useEffect, useState} from "react";
import axios, {get} from "axios";
import {ReflectAdapter as api} from "next/dist/server/web/spec-extension/adapters/reflect";
import userService from "@/services/userService";
export default function Login() {


    const [tab, setTab] = useState("login");
    const [busy, setBusy] = useState(false);
    const [msg, setMsg]   = useState("");

    /* ----- form state ----- */
    const [login,  setLogin]  = useState({ username: "", password: ""});
    const [signup, setSignup] = useState({
        name: "", email: "", username: "", password: "", confirm: ""
    });

    // const handleLogin  = e => { e.preventDefault(); postJSON("/login", login); };

    // useEffect(() => {
    //
    //
    //     console.log(`Login changed:`, login);
    //
    // }, [login]);
    //
    // useEffect(() => {
    //     console.log(`Tab changed. Current tab`, tab);
    // },[tab])


    //----------------use effect xwris kamia parametro/ an to token yparxei tote mhn meineis sthn selida , exeis kanei hdh login---------------------
    useEffect(() => {
        //check if use is already logged in
        const token = localStorage.getItem("token");
        if (token) {
            //Redirect to home page
            window.location.href = "/";
            setMsg("You are already logged in.");
        }
        setMsg("YAA account created successfully. Please log in.");

    },[])



    //-------------------------------------------------------------------------------------------------
    //Basic Auth API
    //edw htan to loginApi apo to userService

    async function handleLogin(e) {
        e.preventDefault();


        console.log("Logging in with:", login);
        console.log("userService:", userService);
        let token= await userService.loginApi(login.username, login.password);
        console.log(`token received: ${token}`);

        //Store token in local storage
        localStorage.setItem("token", token);
        //Redirect to home page
        window.location.href = "/";


    }

    const handleSignup = e => {
        e.preventDefault();
        console.log("Signing up with:", signup);
        // Create a new user with POST /users API

        // On success , change tab  to login page---> setTab("login");
        if (signup.password !== signup.confirm) {
            setMsg("❌ Passwords don’t match"); return;
        }
        const { confirm, ...body } = signup;  // omit confirm field
        userService.SignUpApi(signup.name,signup.username,signup.email,signup.password,signup.confirm);
        //postJSON("/users", body);

        //change tab  to login page
        setTab("login");

    };
//---------------------------RETURN-------------------------------------------------------
    return (
        <div className="auth-shell">
            {/* ---------- tabs ---------- */}
            <div className="tabs">
                <button
                    className={tab === "login" ? "active" : ""}
                    onClick={() => setTab("login")}
                >
                    Log In
                </button>
                <button
                    className={tab === "signup" ? "active" : ""}
                    onClick={() => setTab("signup")}
                >
                    Sign Up
                </button>
            </div>

            {/* ---------- forms ---------- */}
           {tab === "login" && (
                         <form className="form" onSubmit={handleLogin}>
                    <input
                        placeholder="Username"
                        required
                        value={login.username}
                        onChange={e => setLogin({ ...login, username: e.target.value })}
                    />


                    <input
                        type="password"
                        placeholder="Password"
                        required
                        value={login.password}
                        onChange={e => setLogin({ ...login, password: e.target.value })}
                    />
                    <button disabled={busy}>Log In</button>
                </form>
            )}

            {tab === "signup" && (
                <form className="form" onSubmit={handleSignup}>
                    <input
                        placeholder="Name"
                        required
                        value={signup.name}
                        onChange={e => setSignup({ ...signup, name: e.target.value })}
                    />
                    <input
                        type="email"
                        placeholder="Email"
                        required
                        value={signup.email}
                        onChange={e => setSignup({ ...signup, email: e.target.value })}
                    />
                    <input
                        placeholder="Username"
                        required
                        value={signup.username}
                        onChange={e => setSignup({ ...signup, username: e.target.value })}
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        required
                        value={signup.password}
                        onChange={e => setSignup({ ...signup, password: e.target.value })}
                    />
                    <input
                        type="password"
                        placeholder="Confirm password"
                        required
                        value={signup.confirm}
                        onChange={e => setSignup({ ...signup, confirm: e.target.value })}
                    />
                    <button disabled={busy}>Create Account</button>
                </form>
            )}

            {/* ---------- status message ---------- */}
            {msg && <p className="msg">{msg}</p>}
        </div>
    );
}