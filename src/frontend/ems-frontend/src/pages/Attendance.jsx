// import React, { useEffect, useState } from "react";
// import "../Styles/Attendance.css";
//
// const Attendance = () => {
//     const [attendanceList, setAttendanceList] = useState([]);
//     const [message, setMessage] = useState("");
//     const empId = localStorage.getItem("employeeId"); // or get from auth context
//     const token = localStorage.getItem("token");
//
//     const fetchAttendance = async () => {
//         try {
//             const res = await fetch(`http://localhost:8081/attendance/employee/${empId}`, {
//                 headers: {
//                     Authorization: `Bearer ${token}`,
//                 },
//             });
//             const data = await res.json();
//             setAttendanceList(data.reverse()); // latest first
//         } catch (err) {
//             setMessage("Failed to load attendance");
//         }
//     };
//
//     const handleCheckIn = async () => {
//         try {
//             const res = await fetch(`http://localhost:8081/attendance/checkin/${empId}`, {
//                 method: "POST",
//                 headers: {
//                     Authorization: `Bearer ${token}`,
//                 },
//             });
//             const data = await res.json();
//             setMessage("Checked in at " + data.checkIn);
//             fetchAttendance();
//         } catch (err) {
//             setMessage("Check-in failed");
//         }
//     };
//
//     const handleCheckOut = async () => {
//         try {
//             const res = await fetch(`http://localhost:8081/attendance/checkout/${empId}`, {
//                 method: "POST",
//                 headers: {
//                     Authorization: `Bearer ${token}`,
//                 },
//             });
//             const data = await res.json();
//             setMessage("Checked out at " + data.checkOut);
//             fetchAttendance();
//         } catch (err) {
//             setMessage("Check-out failed");
//         }
//     };
//
//     useEffect(() => {
//         fetchAttendance();
//     }, []);
//
//     return (
//         <div className="attendance-container">
//             <h2>Attendance Dashboard</h2>
//
//             {message && <p className="message">{message}</p>}
//
//             <div className="attendance-buttons">
//                 <button onClick={handleCheckIn}>Check In</button>
//                 <button onClick={handleCheckOut}>Check Out</button>
//             </div>
//
//             <table className="attendance-table">
//                 <thead>
//                 <tr>
//                     <th>Date</th>
//                     <th>Check-In</th>
//                     <th>Check-Out</th>
//                     <th>Status</th>
//                 </tr>
//                 </thead>
//                 <tbody>
//                 {attendanceList.map((att) => (
//                     <tr key={att.id}>
//                         <td>{att.date}</td>
//                         <td>{att.checkIn ? att.checkIn : "-"}</td>
//                         <td>{att.checkOut ? att.checkOut : "-"}</td>
//                         <td>{att.status}</td>
//                     </tr>
//                 ))}
//                 </tbody>
//             </table>
//         </div>
//     );
// };
//
// export default Attendance;




import React, { useEffect, useState } from "react";
import "../Styles/Attendance.css";

const Attendance = () => {
    const [attendanceList, setAttendanceList] = useState([]);
    const [message, setMessage] = useState("");
    const empId = localStorage.getItem("employeeId");
    const token = localStorage.getItem("token");

    const fetchAttendance = async () => {
        try {
            const res = await fetch(`http://localhost:8081/attendance/employee/${empId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            const data = await res.json();
            setAttendanceList(data.reverse());
        } catch (err) {
            setMessage("Failed to load attendance");
        }
    };

    const handleCheckIn = async () => {
        try {
            const res = await fetch(`http://localhost:8081/attendance/checkin/${empId}`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            const data = await res.json();
            setMessage("Checked in at " + data.checkIn);
            fetchAttendance();
        } catch (err) {
            setMessage("Check-in failed");
        }
    };

    const handleCheckOut = async () => {
        try {
            const res = await fetch(`http://localhost:8081/attendance/checkout/${empId}`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            const data = await res.json();
            setMessage("Checked out at " + data.checkOut);
            fetchAttendance();
        } catch (err) {
            setMessage("Check-out failed");
        }
    };

    useEffect(() => {
        fetchAttendance();
    }, []);

    return (
        <div className="attendance-container">
            <h2>Attendance Dashboard</h2>

            {message && <p className="message">{message}</p>}

            <div className="attendance-buttons">
                <button onClick={handleCheckIn}>Check In</button>
                <button onClick={handleCheckOut}>Check Out</button>
            </div>

            <div className="summary-cards">
                <div className="summary-card">
                    <h2>9:00 Hrs</h2>
                    <p>Average Working Hours</p>
                </div>
                <div className="summary-card">
                    <h2>9:00 AM</h2>
                    <p>Average In Time</p>
                </div>
                <div className="summary-card">
                    <h2>7:30 PM</h2>
                    <p>Average Out Time</p>
                </div>
                <div className="summary-card">
                    <h2>30:00 Mins</h2>
                    <p>Average Break Time</p>
                </div>
            </div>

            <table className="attendance-table">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Check-In</th>
                    <th>Check-Out</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                {attendanceList.map((att) => (
                    <tr key={att.id}>
                        <td>{att.date}</td>
                        <td>{att.checkIn ? att.checkIn : "-"}</td>
                        <td>{att.checkOut ? att.checkOut : "-"}</td>
                        <td>{att.status}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default Attendance;
