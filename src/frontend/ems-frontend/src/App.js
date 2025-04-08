import React from "react";
import Login from "./pages/Login";
// import { Routes, Route } from 'react-router-dom';
import { BrowserRouter as Router, Routes } from 'react-router-dom';
import {Route} from "react-router-dom"
import {FaUser} from "react-icons/fa";
import ForgotPassword from "./pages/ForgotPassword";

function App() {
  return (
      <Router>
    <Routes>

          <Route path="/" element={<Login />} />
        <Route path = "/forgot-password" element={<ForgotPassword />} />
    </Routes>
      </Router>

  );
}

export default App;
