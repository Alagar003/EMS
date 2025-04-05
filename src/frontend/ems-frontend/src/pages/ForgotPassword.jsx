import React, { useState } from 'react';

const ForgotPassword = () => {
    const [step, setStep] = useState(1); // 1: Request OTP, 2: Verify OTP, 3: Change Password
    const [email, setEmail] = useState('');
    const [otp, setOtp] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [repeatPassword, setRepeatPassword] = useState('');
    const [message, setMessage] = useState('');
    const [loading, setLoading] = useState(false);

    const handleRequestOtp = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMessage('');

        try {
            const response = await fetch(`http://localhost:8081/forgotPassword/verifyMail/${email}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
            });

            const data = await response.text(); // ✅ fix here

            if (response.ok) {
                setMessage(data || 'OTP sent successfully.');
                setStep(2);
            } else {
                setMessage(data || 'Failed to send OTP.');
            }

        } catch (error) {
            setMessage('An error occurred. Please try again later.');
        } finally {
            setLoading(false);
        }
    };

    const handleVerifyOtp = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMessage('');
            try {
                const response = await fetch(`http://localhost:8081/forgotPassword/verifyOtp/${otp}/${email}`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                });

                const data = await response.text(); // ✅ changed to text()

                if (response.ok) {
                    setMessage('OTP verified successfully. Please set your new password.');
                    setStep(3);
                } else {
                    setMessage(data || 'Invalid OTP. Please try again.');
                }
            } catch (error) {
                setMessage('An error occurred. Please try again later.');
            } finally {
            setLoading(false);
        }
    };



    const handleChangePassword = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMessage('');

        if (newPassword !== repeatPassword) {
            setMessage('Passwords do not match.');
            setLoading(false);
            return;
        }

        try {
            const response = await fetch(`http://localhost:8081/forgotPassword/changePassword/${email}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ password: newPassword, repeatPassword }),
            });

            const data = await response.text(); // ✅ use text()

            if (response.ok) {
                setMessage('Password changed successfully. You can now log in.');
                // Optionally redirect to login or reset form
            } else {
                setMessage(data || 'Failed to change password. Please try again.');
            }
        } catch (error) {
            setMessage('An error occurred. Please try again later.');
        } finally {
            setLoading(false);
        }
    };


    return (
        <div>
            <h2>Forgot Password</h2>
            {step === 1 && (
                <form onSubmit={handleRequestOtp}>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="Enter your email"
                        required
                    />
                    <button type="submit" disabled={loading}>
                        {loading ? 'Sending...' : 'Send OTP'}
                    </button>
                </form>
            )}
            {step === 2 && (
                <form onSubmit={handleVerifyOtp}>
                    <input
                        type="text"
                        value={otp}
                        onChange={(e) => setOtp(e.target.value)}
                        placeholder="Enter the OTP"
                        required
                    />
                    <button type="submit" disabled={loading}>
                        {loading ? 'Verifying...' : 'Verify OTP'}
                    </button>
                </form>
            )}
            {step === 3 && (
                <form onSubmit={handleChangePassword}>
                    <input
                        type="password"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                        placeholder="New Password"
                        required
                    />
                    <input
                        type="password"
                        value={repeatPassword}
                        onChange={(e) => setRepeatPassword(e.target.value)}
                        placeholder="Repeat Password"
                        required
                    />
                    <button type="submit" disabled={loading}>
                        {loading ? 'Changing...' : 'Change Password'}
                    </button>
                </form>
            )}
            {message && <p>{message}</p>}
        </div>
    );
};

export default ForgotPassword;