import React, { useEffect, useState } from "react";
import "../Styles/AdminLeaveManagement.css"; // optional for styling

const AdminLeaveManagement = () => {
    const [leaveList, setLeaveList] = useState([]);
    const [message, setMessage] = useState("");

    const token = localStorage.getItem("token"); // admin token

    const fetchLeaves = async () => {
        try {
            const res = await fetch("http://localhost:8081/leave/all", {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            const data = await res.json();
            setLeaveList(data);
        } catch (error) {
            console.error("Error fetching leaves", error);
        }
    };

    const updateStatus = async (leaveId, newStatus) => {
        try {
            const res = await fetch(
                `http://localhost:8081/leave/${leaveId}/status?status=${newStatus}`,
                {
                    method: "PUT",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            if (res.ok) {
                setMessage(`Leave ID ${leaveId} updated to ${newStatus}`);
                fetchLeaves(); // refresh list
            } else {
                setMessage("Failed to update status");
            }
        } catch (error) {
            console.error("Update failed", error);
            setMessage("Update error");
        }
    };

    useEffect(() => {
        fetchLeaves();
    }, []);

    return (
        <div className="admin-leave-container">
            <h2>Admin Leave Management</h2>
            {message && <p className="message">{message}</p>}

            <table className="leave-table">
                <thead>
                <tr>
                    <th>Employee ID</th>
                    <th>Leave Type</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Reason</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                {leaveList.map((leave) => (
                    <tr key={leave.id}>
                        <td>{leave.employee.id}</td>
                        <td>{leave.leaveType}</td>
                        <td>{leave.fromDate}</td>
                        <td>{leave.toDate}</td>
                        <td>{leave.reason}</td>
                        <td>{leave.status}</td>
                        <td>
                            <select
                                value={leave.status}
                                onChange={(e) =>
                                    updateStatus(leave.id, e.target.value)
                                }
                            >
                                <option value="PENDING">Pending</option>
                                <option value="APPROVED">Approve</option>
                                <option value="REJECTED">Reject</option>
                            </select>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default AdminLeaveManagement;
