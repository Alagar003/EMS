package com.example.demo.repositories;

import com.example.demo.model.Admin;
import com.example.demo.model.Employee;
import com.example.demo.model.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.admin = ?2")
    Optional<ForgotPassword> findByOtpAAndAdmin(Integer otp, Admin admin);


    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.employee = ?2")
    Optional<ForgotPassword> findByOtpAAndEmployee(Integer otp, Employee employee);

    Optional<ForgotPassword> findByAdminIdOrEmployeeId(Long adminId, Long employeeId);

//    Optional<ForgotPassword> findByOtpAndAdmin(Integer otp, Admin admin);
//    Optional<ForgotPassword> findByOtpAndEmployee(Integer otp, Employee employee);



}
