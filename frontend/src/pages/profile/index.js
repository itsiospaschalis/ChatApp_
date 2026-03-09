// import { useState, useEffect } from "react";
// import axios from "axios";
// import userService from "@/services/userService";
//
// export default function Profile() {
//     const [user, setUser] = useState({ username: "...", email: "...", name: "...", role: "..." });
//     const [isEditing, setIsEditing] = useState(false);
//     const [formData, setFormData] = useState({ name: "", username: "", email: "", password: "" });
//
//     useEffect(() => {
//         const fetchUser = async () => {
//             const token = localStorage.getItem("token");
//             if (!token) {
//                 window.location.href = "/login";
//                 return;
//             }
//
//             const jwtPayload = userService.jwtPayload(token);
//             let res = await axios.get(`http://localhost:8080/users/${jwtPayload.sub}`, {
//                 headers: { Authorization: `Bearer ${token}` },
//             });
//
//             setUser(res.data);
//             setFormData({ ...res.data, password: "" }); // initialize form with user data
//         };
//
//         fetchUser();
//     }, []);
//
//     const handleChange = (e) => {
//         setFormData({ ...formData, [e.target.name]: e.target.value });
//     };
//
//     const handleSave = async (e) => {
//         e.preventDefault();
//         const token = localStorage.getItem("token");
//
//         try {
//             let res = await axios.put(`http://localhost:8080/users/${user.username}`, formData, {
//                 headers: { Authorization: `Bearer ${token}` },
//             });
//             setUser(res.data);
//             setIsEditing(false); // switch back to profile view
//         } catch (err) {
//             console.error("Update failed:", err);
//             alert("Failed to update profile.");
//         }
//     };
//
//     return (
//         <div className="profile-page">
//             <header>
//                 <h1>Profile</h1>
//             </header>
//
//             {/* 👇 this is where we switch view vs edit, same as your login/signup */}
//             {!isEditing ? (
//                 <>
//                     <p><b>Name:</b> {user.name}</p>
//                     <p><b>Username:</b> @{user.username}</p>
//                     <p><b>Email:</b> {user.email}</p>
//                     <p><b>Role:</b> {user.role}</p>
//
//                     <button onClick={() => setIsEditing(true)}>Edit Profile</button>
//                 </>
//             ) : (
//                 <form className="form" onSubmit={handleSave}>
//                     <input
//                         name="name"
//                         placeholder="Name"
//                         value={formData.name}
//                         onChange={handleChange}
//                     />
//                     <input
//                         name="username"
//                         placeholder="Username"
//                         value={formData.username}
//                         onChange={handleChange}
//                     />
//                     <input
//                         type="email"
//                         name="email"
//                         placeholder="Email"
//                         value={formData.email}
//                         onChange={handleChange}
//                     />
//                     <input
//                         type="password"
//                         name="password"
//                         placeholder="New Password"
//                         value={formData.password}
//                         onChange={handleChange}
//                     />
//
//                     <button type="submit">Save</button>
//                     <button type="button" onClick={() => setIsEditing(false)}>Cancel</button>
//                 </form>
//             )}
//         </div>
//     );
// }


import { useState, useEffect } from "react";
import axios from "axios";
import userService from "../../services/userService";

export default function Profile() {
    const [user, setUser] = useState({ username: "...", email: "...", role: "..." });
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({ name: "", username: "", email: "", password: "" });

    useEffect(() => {
        const fetchUser = async () => {
            const token = localStorage.getItem("token");
            if (!token) {
                window.location.href = "/login";
                return;
            }
            try {
                const jwtPayload = userService.jwtPayload(token);
                const res = await axios.get(`http://localhost:8080/users/${jwtPayload.sub}`, {
                    headers: { Authorization: `Bearer ${token}` },
                });
                setUser(res.data);
                setFormData({
                    name: res.data.name,
                    username: res.data.username,
                    email: res.data.email,
                    password: "",
                });
            } catch (err) {
                console.error("Error fetching user:", err);
            }
        };
        fetchUser();
    }, []);

    const handleChange = (e) => {
        setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
    };

    const handleSave = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem("token");
            await axios.put(`http://localhost:8080/users/${user.username}`, formData, {
                headers: { Authorization: `Bearer ${token}` },
            });
            setUser({ ...user, ...formData });
            setIsEditing(false);
        } catch (err) {
            console.error("Error saving user:", err);
        }
    };

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
        <div className="home profile-page">
            <div className="profile-wrapper">
                <header className="profile-header">
                    <img
                        src={user.avatar || "/images/default-avatar.png"}
                        alt={`${user.name}'s avatar`}
                        className="profile-avatar"
                    />
                    <div className="profile-header-text">
                        <h1 className="profile-title">Profile</h1>
                        <p className="profile-name">{user.name}</p>
                        <span className={`profile-role-badge profile-role--${user.role?.toLowerCase() || "default"}`}>
              {user.role}
            </span>
                    </div>
                </header>

                {!isEditing ? (
                    <>
                        <dl className="profile-fields">
                            <div className="profile-field">
                                <dt>Name</dt>
                                <dd>{user.name}</dd>
                            </div>
                            <div className="profile-field">
                                <dt>Username</dt>
                                <dd>@{user.username}</dd>
                            </div>
                            <div className="profile-field">
                                <dt>Email</dt>
                                <dd>
                                    <a href={`mailto:${user.email}`} className="profile-email-link">
                                        {user.email}
                                    </a>
                                </dd>
                            </div>
                            <div className="profile-field">
                                <dt>Role</dt>
                                <dd>
                  <span className={`profile-role-tag profile-role--${user.role?.toLowerCase() || "default"}`}>
                    {user.role}
                  </span>
                                </dd>
                            </div>
                        </dl>

                        <div className="profile-ctas">
                            <button onClick={() => setIsEditing(true)} className="profile-btn profile-btn-primary">
                                Edit Profile
                            </button>

                            {/*<a href="/logout" className="profile-btn profile-btn-secondary">*/}
                            {/*    Sign Out*/}
                            {/*</a>*/}

                            {/*<a href="/logout" className="profile-btn profile-btn-secondary"onClick={logout}>Sign out</a>*/}

                            {loggedIn &&(
                                <a href="#" className="profile-btn profile-btn-secondary"onClick={logout}>Logout</a>
                            )}

                        </div>
                    </>
                ) : (
                    <form className="form" onSubmit={handleSave}>
                        <input name="name" placeholder="Name" value={formData.name} onChange={handleChange} />
                        <input name="username" placeholder="Username" value={formData.username} onChange={handleChange} />
                        <input type="email" name="email" placeholder="Email" value={formData.email} onChange={handleChange} />
                        <input type="password" name="password" placeholder="New Password" value={formData.password} onChange={handleChange} />

                        <div className="profile-ctas">
                            <button type="submit" className="profile-btn profile-btn-primary">
                                Save
                            </button>
                            <button type="button" onClick={() => setIsEditing(false)} className="profile-btn profile-btn-secondary">
                                Cancel
                            </button>
                        </div>
                    </form>
                )}
            </div>
        </div>
    );
}
