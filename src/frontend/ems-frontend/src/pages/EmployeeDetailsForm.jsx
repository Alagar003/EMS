// import React, { useState } from 'react';
// import axios from 'axios';
//
// const EmployeeDetailsForm = () => {
//     const [employee, setEmployee] = useState({
//         firstName: '',
//         lastName: '',
//         username: '',
//         email: '',
//         phone: '',
//         gender: '',
//         dob: '',
//         address: '',
//         city: '',
//         bloodGroup: '',
//         emergencyNumber: '',
//         languagesKnown: '',
//         maritalStatus: '',
//     });
//
//     const [resume, setResume] = useState(null);
//     const [offerLetter, setOfferLetter] = useState(null);
//     const [joiningLetter, setJoiningLetter] = useState(null);
//
//     const handleChange = (e) => {
//         setEmployee({
//             ...employee,
//             [e.target.name]: e.target.value,
//         });
//     };
//
//     const handleFileChange = (e, setter) => {
//         setter(e.target.files[0]);
//     };
//
//     const handleSubmit = async (e) => {
//         e.preventDefault();
//
//         try {
//             const token = localStorage.getItem('token');
//
//             const res = await axios.post(
//                 "http://localhost:8081/admin/update-employee",
//                 employee, // plain JSON
//                 {
//                     headers: {
//                         'Authorization': `Bearer ${token}`,
//                         'Content-Type': 'application/json',
//                     },
//                 }
//             );
//             alert(res.data.message || "Employee updated successfully");
//         } catch (err) {
//             console.error(err);
//             alert(err.response?.data?.message || "Error submitting form");
//         }
//     };
//
//
//     return (
//         <div className="form-container">
//             <h2>Employee Details</h2>
//             <form onSubmit={handleSubmit} encType="multipart/form-data">
//                 <input name="firstName" placeholder="First Name" onChange={handleChange} required />
//                 <input name="lastName" placeholder="Last Name" onChange={handleChange} required />
//                 <input name="username" placeholder="Username" onChange={handleChange} required />
//                 <input name="email" type="email" placeholder="Email" onChange={handleChange} required />
//                 <input name="phone" placeholder="Phone Number" onChange={handleChange} required />
//                 <select name="gender" onChange={handleChange} required>
//                     <option value="">Select Gender</option>
//                     <option>Male</option>
//                     <option>Female</option>
//                     <option>Other</option>
//                 </select>
//                 <input name="dob" type="date" onChange={handleChange} required />
//                 <input name="address" placeholder="Address" onChange={handleChange} required />
//                 <input name="city" placeholder="City" onChange={handleChange} required />
//                 <input name="bloodGroup" placeholder="Blood Group" onChange={handleChange} />
//                 <input name="emergencyNumber" placeholder="Emergency Number" onChange={handleChange} />
//                 <input name="languagesKnown" placeholder="Languages Known" onChange={handleChange} />
//                 <select name="maritalStatus" onChange={handleChange} required>
//                     <option value="">Marital Status</option>
//                     <option>Married</option>
//                     <option>Unmarried</option>
//                 </select>
//
//                 {/* File Uploads */}
//                 <label>Upload Resume</label>
//                 <input type="file" onChange={(e) => handleFileChange(e, setResume)} />
//
//                 <label>Upload Offer Letter</label>
//                 <input type="file" onChange={(e) => handleFileChange(e, setOfferLetter)} />
//
//                 <label>Upload Joining Letter</label>
//                 <input type="file" onChange={(e) => handleFileChange(e, setJoiningLetter)} />
//
//                 <button type="submit">Save</button>
//             </form>
//         </div>
//     );
// };
//
// export default EmployeeDetailsForm;






import React, { useState } from 'react';
import '../Styles/EmployeeDetailsForm.css';

const EmployeeDetailsForm = () => {
    const [employee, setEmployee] = useState({
        firstName: '',
        lastName: '',
        username: '',
        email: '',
        phone: '',
        gender: '',
        dob: '',
        address: '',
        city: '',
        bloodGroup: '',
        emergencyNumber: '',
        languagesKnown: '',
        maritalStatus: ''
    });

    const [resume, setResume] = useState(null);
    const [offerLetter, setOfferLetter] = useState(null);
    const [joiningLetter, setJoiningLetter] = useState(null);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEmployee({ ...employee, [name]: value });
    };

    const handleFileChange = (e, setter) => {
        setter(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData();

        Object.entries(employee).forEach(([key, value]) => {
            formData.append(key, value);
        });

        if (resume) formData.append('resumeFile', resume);
        if (offerLetter) formData.append('offerLetterFile', offerLetter);
        if (joiningLetter) formData.append('joiningLetterFile', joiningLetter);

        try {
            const token = localStorage.getItem('token');

            const res = await fetch(`http://localhost:8081/admin/update-${employee.email}`, {
                method: 'POST',
                body: formData,
                headers: {
                    Authorization: `Bearer ${token}`
                    // Don't add 'Content-Type' if using FormData — the browser will set it with boundary automatically
                }
            });

            if (!res.ok) {
                const errorText = await res.text();
                throw new Error(`Error ${res.status}: ${errorText}`);
            }

            const data = await res.json(); // only parse if it's actually valid JSON
            alert(data.message || "Employee updated successfully!");
        } catch (err) {
            alert("Error updating employee.");
            console.error("Update error:", err);
        }
    }
    return (
        <div className="employee-form-container">
            <h2>Update Employee Details</h2>
            <form className="employee-form" onSubmit={handleSubmit}>
                <div className="form-group">
                    <input name="firstName" value={employee.firstName} onChange={handleChange} placeholder="First Name"
                           required/>
                    <input name="lastName" value={employee.lastName} onChange={handleChange} placeholder="Last Name"
                           required/>
                    <input name="username" value={employee.username} onChange={handleChange} placeholder="Username"
                           required/>
                </div>

                <div className="form-group">
                    <input name="email" value={employee.email} onChange={handleChange} placeholder="Email" required/>
                    <input name="phone" value={employee.phone} onChange={handleChange} placeholder="Phone Number"
                           required/>
                    <select name="gender" value={employee.gender} onChange={handleChange} required>
                        <option value="">Gender</option>
                        <option value="MALE">Male</option>
                        <option value="FEMALE">Female</option>
                    </select>
                </div>

                <div className="form-group">
                    <input name="dob" type="date" value={employee.dob} onChange={handleChange}
                           placeholder="Date of Birth" required/>
                    <input name="address" value={employee.address} onChange={handleChange} placeholder="Address"
                           required/>
                    <input name="city" value={employee.city} onChange={handleChange} placeholder="City" required/>
                </div>

                <div className="form-group">
                    <input name="bloodGroup" value={employee.bloodGroup} onChange={handleChange}
                           placeholder="Blood Group"/>
                    <input name="emergencyNumber" value={employee.emergencyNumber} onChange={handleChange}
                           placeholder="Emergency Number"/>
                    <input name="languagesKnown" value={employee.languagesKnown} onChange={handleChange}
                           placeholder="Languages Known"/>
                    {/*<input name="maritalStatus" value={employee.maritalStatus} onChange={handleChange} placeholder="Marital Status" />*/}
                    <label>Marital Status</label>
                    <select
                        name="maritalStatus"
                        value={employee.maritalStatus}
                        onChange={handleChange}
                    >
                        <option value="">-- Select --</option>
                        <option value="Married">Married</option>
                        <option value="UnMarried">UnMarried</option>
                    </select>

                </div>

                <div className="form-group">
                    <label>Resume: <input type="file" onChange={(e) => handleFileChange(e, setResume)}/></label>
                    <label>Offer Letter: <input type="file"
                                                onChange={(e) => handleFileChange(e, setOfferLetter)}/></label>
                    <label>Joining Letter: <input type="file" onChange={(e) => handleFileChange(e, setJoiningLetter)}/></label>
                </div>


                <div className="section">
                    <h3>Company Details</h3>
                    <input type="text" name="employeeId" placeholder="Employee Id" onChange={handleChange}/>
                    <input type="text" name="department" placeholder="Department" onChange={handleChange}/>
                    <input type="text" name="designation" placeholder="Designation" onChange={handleChange}/>
                    <input type="date" name="dateOfJoining" placeholder="Date of Joining" onChange={handleChange}/>
                    <input type="date" name="dateOfLeaving" placeholder="Date of Leaving" onChange={handleChange}/>
                    <select name="status" onChange={handleChange}>
                        <option value="">Select Status</option>
                        <option value="Currently Working">Currently Working</option>
                        <option value="Resigned">Resigned</option>
                    </select>
                </div>

                <div className="section">
                    <h3>Bank Account Details</h3>
                    <input type="text" name="bankHolderName" placeholder="Bank Holder Name" onChange={handleChange}/>
                    <input type="text" name="branchName" placeholder="Branch Name" onChange={handleChange}/>
                    <input type="text" name="bankName" placeholder="Bank Name" onChange={handleChange}/>
                    <input type="text" name="accountNumber" placeholder="Account Number" onChange={handleChange}/>
                    <input type="text" name="ifscCode" placeholder="IFSC Code" onChange={handleChange}/>
                    <input type="text" name="panCardNumber" placeholder="PAN Card Number" onChange={handleChange}/>
                </div>


                <button type="submit" className="submit-btn">Update Profile</button>
            </form>
        </div>
    );
};

export default EmployeeDetailsForm;
