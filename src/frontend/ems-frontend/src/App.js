import React from "react";
import Login from "./pages/Login";
// import { Routes, Route } from 'react-router-dom';
import EmployeeDetailsForm from "./pages/EmployeeDetailsForm"
import { BrowserRouter as Router, Routes } from 'react-router-dom';
import {Route} from "react-router-dom"
import {FaUser} from "react-icons/fa";
import ForgotPassword from "./pages/ForgotPassword";
// import EmployeeDashboard from "./pages/EmployeeDashBoard";
import EmployeeDashboard from "./pages/EmployeeDashboard";
import CreateEmployee from "./pages/CreateEmployee";
import Attendance from "./pages/Attendance";
import Leave from "./pages/LeaveRequest";
import AdminLeaveManagement from "./pages/AdminLeaveManagement";
function App() {
  return (
      <Router>
    <Routes>
        <Route path="/" element={<Login />} />
        <Route path = "/forgot-password" element={<ForgotPassword />} />
        <Route path="/add-employee" element={<EmployeeDetailsForm />} />
        <Route path="/employee-list" element={<EmployeeDashboard />} />
        <Route path="/create-employee" element={<CreateEmployee />} />
        <Route path="/attendance" element={<Attendance />} />
        <Route path="leave" element={<Leave />} />
        <Route path="update-leave" element={<AdminLeaveManagement />} />

    </Routes>
      </Router>

  );
}

export default App;
