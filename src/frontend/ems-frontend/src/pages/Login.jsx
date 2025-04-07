import React, { useState, useCallback } from "react";
import { FaUser, FaLock } from "react-icons/fa";
import "../Styles/Login.css";
import loginimage from "../images/login.png";
import { useNavigate } from 'react-router-dom';


const Login = () => {
    const [role, setRole] = useState("employee");
    const [input, setInput] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const history = useNavigate();

    const handleLogin = useCallback(async (e) => {
        e.preventDefault();
        if (!input || !password) {
            alert("Please fill in all fields.");
            return;
        }

        setLoading(true);
        const loginData = {
            usernameOrEmail: input,
            password,
        };

        const endpoint = role === "admin"
            ? "http://localhost:8081/admin/login"
            : "http://localhost:8081/admin/employee/login";

        try {
            const response = await fetch(endpoint, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                credentials: "include",
                body: JSON.stringify(loginData),
            });

            const data = await response.json();
            // if (response.ok) {
            //     alert("Login Successful");
            //     localStorage.setItem("token", data.token);
            //     setInput("");
            //     setPassword("");
            //     // na(`/${role}-dashboard`);  // Redirect based on role

            if (response.ok) {
                alert("Login Successful");
                localStorage.setItem("token", data.token);

                console.log(localStorage.getItem("token"));

                setInput("");
                setPassword("");

                if (role === "admin") {
                    navigate("/add-employee"); // 👈 Admin redirected to Add Employee form
                } else {
                    navigate("/"); // 👈 Employee redirected to dashboard
                }
            } else {
                alert(data.message || "An error occurred. Please check your credentials and try again.");
            }

        } catch (error) {
            console.error("Login Error:", error);
            alert("An unexpected error occurred. Please try again later.");
        } finally {
            setLoading(false);
        }
    }, [input, password, role, history]);

    const handleForgotPassword = () => {
        navigate('/forgot-password');
    };

    return (
        <div className="login-container">
            <div className="login-left">
                <img src={loginimage} alt="Team working" className="login-image" />
                <h1>Grow Your <span>Workspace</span> Experience</h1>
                <p>It is certainly important because it is only through hard work that we can achieve the goals of our life. Thus, we all must work hard.</p>
            </div>

            <div className="login-right">
                <h2>{role === "admin" ? "Admin Login" : "Employee Login"}</h2>
                <form onSubmit={handleLogin}>
                    <div className="role-selector">
                        <label>
                            <input
                                type="radio"
                                value="employee"
                                checked={role === "employee"}
                                onChange={() => setRole("employee")}
                            />
                            Employee
                        </label>
                        <label>
                            <input
                                type="radio"
                                value="admin"
                                checked={role === "admin"}
                                onChange={() => setRole("admin")}
                            />
                            Admin
                        </label>
                    </div>

                    <div className="input-group">
                        <FaUser className="icon" />
                        <input
                            type="text"
                            placeholder="Enter Username or Email"
                            value={input}
                            onChange={(e) => setInput(e.target.value)}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <FaLock className="icon" />
                        <input
                            type="password"
                            placeholder="Password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    <div className="forgot-password">
                        <p onClick={handleForgotPassword} style={{cursor: 'pointer', color: 'blue'}}>
                            Forgot Password?
                        </p>
                    </div>

                    <button type="submit" className="login-btn" disabled={loading}>
                        {loading ? "Logging in..." : "Login"}
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Login;