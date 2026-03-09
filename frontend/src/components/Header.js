import Head from "next/head";
import {useEffect, useState} from "react";

export default function Header() {

let [loggedIn, setLoggedIn] = useState(false);

    useEffect(() => {
        let token = localStorage.getItem("token");
        if (token) {
            setLoggedIn(true);
        } else {
            setLoggedIn(false);
        }
    }, [])

    function logout() {
        // axios.post("http://localhost:3000/logout")
        //remove token from local storage
        localStorage.removeItem("token");
        //update state
        setLoggedIn(false);
        //redirect to login page
        window.location.href = "/login";
    }

    return (

        <div className="header">
            <div className="header-menu">
                <a href="/" className="menu-item">Home</a>
                {/*<a href="https://github.com/itsiospaschalis/ChatApp" className="menu-item">GitHub</a>*/}
                <a href="/profile" className="menu-item">Profile</a>
                {loggedIn &&(
                    <a href="#" className="menu-item"onClick={logout}>Logout</a>
                )}
            </div>
        </div>

    );
}
