// import React, { useEffect, useState } from 'react';
// import '../Styles/EmployeeDashboard.css'; // optional styling file
//
// const EmployeeDashboard = () => {
//     const [employees, setEmployees] = useState([]);
//
//     useEffect(() => {
//         fetch('http://localhost:8081/admin/allemployees')
//             .then((res) => res.json())
//             .then((data) => setEmployees(data))
//             .catch((err) => console.error('Error fetching employees:', err));
//     }, []);
//
//     const viewMore = (employee) => {
//         alert(`Viewing more about ${employee.firstName} ${employee.lastName}`);
//         // You can implement navigation or modal popup here
//     };
//
//     return (
//         <div className="dashboard">
//             <div className="dashboard-header">
//                 <h2>{employees.length} Employee{employees.length !== 1 && 's'}</h2>
//                 <div className="controls">
//                     <input type="text" placeholder="Search" className="search-input" />
//                     <button className="btn filter">Filter</button>
//                     <button className="btn add">+ Add Employees</button>
//                 </div>
//             </div>
//             <div className="employee-grid">
//                 {employees.map((emp) => (
//                     <div className="employee-card" key={emp.id}>
//                         <img
//                             src={emp.profileImageUrl || '/default-avatar.png'}
//                             alt={`${emp.firstName} ${emp.lastName}`}
//                             className="profile-img"
//                         />
//                         <h3>{emp.firstName} {emp.lastName}</h3>
//                         <p className="designation">{emp.designation}</p>
//                         <span className={`status ${emp.status.toLowerCase()}`}>{emp.status}</span>
//                         <p>Department: {emp.department}</p>
//                         <p>Hired Date: {emp.hiredDate}</p>
//                         <p>Email: {emp.email}</p>
//                         <p>Phone: {emp.phone}</p>
//                         <button className="btn view-more" onClick={() => viewMore(emp)}>View More</button>
//                     </div>
//                 ))}
//             </div>
//         </div>
//     );
// };
//
// export default EmployeeDashboard;







import React, { useEffect, useState } from 'react';
import '../Styles/EmployeeDashboard.css';
import {useNavigate} from "react-router-dom"; // optional styling file

const EmployeeDashboard = () => {
    const [employees, setEmployees] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetch('http://localhost:8081/admin/allemployees')
            .then((res) => res.json())
            .then((data) => setEmployees(data))
            .catch((err) => console.error('Error fetching employees:', err));
    }, []);

    const viewMore = (employee) => {
        alert(`Viewing more about ${employee.firstName || ''} ${employee.lastName || ''}`);
        // You can implement navigation or modal popup here
    };

     const handleAddEmployee = async (employee) => {
        navigate("/create-employee");
        }
    return (
        <div className="dashboard">
            <div className="dashboard-header">
                <h2>{employees.length} Employee{employees.length !== 1 && 's'}</h2>
                <div className="controls">
                    <input type="text" placeholder="Search" className="search-input" />
                    <button className="btn filter">Filter</button>
                    <button className="btn add" onClick={handleAddEmployee}>+ Add Employees</button>
                </div>
            </div>
            <div className="employee-grid">
                {employees.map((emp) => (
                    <div className="employee-card" key={emp.id}>
                        <img
                            src={emp.profileImageUrl || '/default-avatar.png'}
                            alt={`${emp.firstName || ''} ${emp.lastName || ''}`}
                            className="profile-img"
                        />
                        <h3>{emp.firstName || ''} {emp.lastName || ''}</h3>
                        <p className="designation">{emp.designation || 'N/A'}</p>
                        <span className={`status ${emp.status?.toLowerCase() || 'unknown'}`}>{emp.status || 'Unknown'}</span>
                        <p>Department: {emp.department || 'N/A'}</p>
                        <p>Hired Date: {emp.hiredDate || 'N/A'}</p>
                        <p>Email: {emp.email || 'N/A'}</p>
                        <p>Phone: {emp.phone || 'N/A'}</p>
                        <button className="btn view-more" onClick={() => viewMore(emp)}>View More</button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default EmployeeDashboard;
