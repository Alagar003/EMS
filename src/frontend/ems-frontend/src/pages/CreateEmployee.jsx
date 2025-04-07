import React, { useState } from "react";
import axios from "axios";
import "../Styles/CreateEmployee.css"

const CreateEmployee = () => {
    const [employee, setEmployee] = useState({
        firstName: "",
        lastName: "",
        email: "",
        username: "",
        password: "",
        phone: "",
        gender: "",
        dob: "",
        address: "",
    });

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const handleChange = (e) => {
        setEmployee({ ...employee, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage("");
        setError("");

        try {
            const token = localStorage.getItem("token"); // ✅ match key with login
            console.log("Admin Token:", token); // ✅ check it prints

            const response = await axios.post("http://localhost:8081/admin/create-employee", employee, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });


            setMessage(response.data.message);
            setEmployee({
                firstName: "",
                lastName: "",
                email: "",
                username: "",
                password: "",
                phone: "",
                gender: "",
                dob: "",
                address: "",
            });
        } catch (err) {
            setError(err.response?.data?.message || "Something went wrong!");
        }
    };

    return (
        <div className="create-employee-form">
            <h2>Create Employee</h2>
            {message && <p style={{ color: "green" }}>{message}</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}
            <form onSubmit={handleSubmit}>
                <input type="text" name="firstName" placeholder="First Name" value={employee.firstName} onChange={handleChange} required />
                <input type="text" name="lastName" placeholder="Last Name" value={employee.lastName} onChange={handleChange} required />
                <input type="email" name="email" placeholder="Email" value={employee.email} onChange={handleChange} required />
                <input type="text" name="username" placeholder="Username" value={employee.username} onChange={handleChange} required />
                <input type="password" name="password" placeholder="Password" value={employee.password} onChange={handleChange} required />
                <input type="text" name="phone" placeholder="Phone Number" value={employee.phone} onChange={handleChange} />
                <select name="gender" value={employee.gender} onChange={handleChange}>
                    <option value="">Select Gender</option>
                    <option value="MALE">Male</option>
                    <option value="FEMALE">Female</option>
                    <option value="OTHER">Other</option>
                </select>
                <input type="date" name="dob" placeholder="Date of Birth" value={employee.dob} onChange={handleChange} />
                <textarea name="address" placeholder="Address" value={employee.address} onChange={handleChange}></textarea>

                <button type="submit">Create Employee</button>
            </form>
        </div>
    );
};

export default CreateEmployee;
