// import React, { useEffect, useState } from "react";
// import "../Styles/LeaveRequest.css";
//
// const LeaveRequest = () => {
//     const [leaves, setLeaves] = useState([]);
//     const empId = localStorage.getItem("employeeId");
//     const token = localStorage.getItem("token");
//
//     useEffect(() => {
//         const fetchLeaves = async () => {
//             try {
//                 const res = await fetch(`http://localhost:8081/leave/employee/${empId}`, {
//                     headers: {
//                         Authorization: `Bearer ${token}`,
//                     },
//                 });
//                 const data = await res.json();
//                 setLeaves(data.reverse());
//             } catch (err) {
//                 console.error("Error fetching leave data", err);
//             }
//         };
//
//         fetchLeaves();
//     }, [empId, token]);
//
//     return (
//         <div className="leave-container">
//             <h2>Leave Requests</h2>
//             <table className="leave-table">
//                 <thead>
//                 <tr>
//                     <th>Leave Type</th>
//                     <th>From</th>
//                     <th>To</th>
//                     <th>Status</th>
//                 </tr>
//                 </thead>
//                 <tbody>
//                 {leaves.map((leave) => (
//                     <tr key={leave.id}>
//                         <td>{leave.leaveType}</td>
//                         <td>{leave.startDate}</td>
//                         <td>{leave.endDate}</td>
//                         <td className={`status ${leave.status.toLowerCase()}`}>
//                             {leave.status}
//                         </td>
//                     </tr>
//                 ))}
//                 </tbody>
//             </table>
//         </div>
//     );
// };
//
// export default LeaveRequest;



import React, { useEffect, useState } from "react";
import "../Styles/LeaveRequest.css";

const Leave = () => {
    const [leaves, setLeaves] = useState([]);
    const [leaveType, setLeaveType] = useState("");
    const [reason, setReason] = useState("");
    const [fromDate, setFromDate] = useState("");
    const [toDate, setToDate] = useState("");
    const [message, setMessage] = useState("");

    const empId = localStorage.getItem("employeeId");
    const token = localStorage.getItem("token");

    const fetchLeaves = async () => {
        try {
            const res = await fetch(`http://localhost:8081/leave/employee/${empId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            const data = await res.json();
            setLeaves(data.reverse());
        } catch (err) {
            console.error(err);
            setMessage("Failed to load leaves");
        }
    };

    const handleApply = async (e) => {
        e.preventDefault();

        const leave = {
            leaveType,
            reason,
            fromDate,
            toDate,
            employee: { id: empId },
        };

        try {
            const res = await fetch("http://localhost:8081/leave/apply", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(leave),
            });

            const data = await res.json();
            setMessage("Leave applied successfully");
            setLeaveType("");
            setReason("");
            setFromDate("");
            setToDate("");
            fetchLeaves();
        } catch (err) {
            console.error(err);
            setMessage("Leave application failed");
        }
    };

    useEffect(() => {
        fetchLeaves();
    }, []);

    return (
        <div className="leave-container">
            <h2>Leave Management</h2>

            {message && <p className="leave-message">{message}</p>}

            <form onSubmit={handleApply} className="leave-form">
                <label>Leave Type:</label>
                <select value={leaveType} onChange={(e) => setLeaveType(e.target.value)} required>
                    <option value="">Select</option>
                    <option value="SICK">Sick</option>
                    <option value="CASUAL">Casual</option>
                    <option value="EMERGENCY">Emergency</option>
                </select>

                <label>Reason:</label>
                <input type="text" value={reason} onChange={(e) => setReason(e.target.value)} required />

                <label>From Date:</label>
                <input type="date" value={fromDate} onChange={(e) => setFromDate(e.target.value)} required />

                <label>To Date:</label>
                <input type="date" value={toDate} onChange={(e) => setToDate(e.target.value)} required />

                <button type="submit">Apply Leave</button>
            </form>

            <table className="leave-table">
                <thead>
                <tr>
                    <th>Type</th>
                    <th>Reason</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                {leaves.map((l) => (
                    <tr key={l.id}>
                        <td>{l.leaveType}</td>
                        <td>{l.reason}</td>
                        <td>{l.fromDate}</td>
                        <td>{l.toDate}</td>
                        <td>{l.status}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default Leave;
