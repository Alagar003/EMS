package com.example.demo.service;

import com.example.demo.dto.UpdateEmployeeProfileDTO;
import com.example.demo.model.Admin;
import com.example.demo.model.Employee;
import com.example.demo.model.Role;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminRepository adminRepository;


//    public String loginEmployee(String input, String password) {
//        Optional<Employee> employeeOpt = employeeRepository.findByEmail(input);
//        if (employeeOpt.isEmpty()) {
//            employeeOpt = employeeRepository.findByUsername(input);
//        }
//
//        if (employeeOpt.isPresent()) {
//            Employee employee = employeeOpt.get();
//            if (employee.getPassword().equals(password)) {
//                return jwtUtil.generateToken(employee.getUsername(), "ROLE_" + employee.getRole().name());
//            }
//        }
//        return null;
//    }





    public String loginEmployee(String input, String password) {
        Optional<Employee> employeeOpt = employeeRepository.findByEmail(input);
        if (employeeOpt.isEmpty()) {
            employeeOpt = employeeRepository.findByUsername(input);
        }

        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            if (password.equals(employee.getPassword())) {  // ✅ Compare directly
                return jwtUtil.generateToken(employee.getUsername(), "ROLE_" + employee.getRole().name());
            }
        }
        return null;
    }

//    public ResponseEntity<?> createEmployee(Employee employee) {
//        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
//            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email already exists!"));
//        }
//
//        employee.setRole(Role.EMPLOYEE); // ✅ Set role to EMPLOYEE
//        employeeRepository.save(employee);
//
//        return ResponseEntity.ok(Collections.singletonMap("message", "Employee created successfully!"));
//    }

    public void createEmployee(Employee employee) {
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists!");
        }

        if (employeeRepository.findByUsername(employee.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists!");
        }

        employee.setRole(Role.EMPLOYEE);
        employeeRepository.save(employee);
    }



//    public Employee updateProfile(String email, UpdateEmployeeProfileDTO dto) {
//        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
//        if (optionalEmployee.isEmpty()) {
//            throw new RuntimeException("Employee not found");
//        }
//
//        Employee employee = optionalEmployee.get();
//        employee.setFirstName(dto.getFirstName());
//        employee.setLastName(dto.getLastName());
//        employee.setUsername(dto.getUsername());
//        employee.setPhone(dto.getPhone());
//        employee.setGender(dto.getGender());
//        employee.setDob(dto.getDob());
//        employee.setAddress(dto.getAddress());
//        employee.setCity(dto.getCity());
//        employee.setBloodGroup(dto.getBloodGroup());
//        employee.setEmergencyNumber(dto.getEmergencyNumber());
//        employee.setLanguagesKnown(dto.getLanguagesKnown());
//        employee.setMaritalStatus(dto.getMaritalStatus());
//        employee.setResumeFilePath(dto.getResumeFilePath());
//        employee.setOfferLetterFilePath(dto.getOfferLetterFilePath());
//        employee.setJoiningLetterFilePath(dto.getJoiningLetterFilePath());
//
//        return employeeRepository.save(employee);
//    }




//    public Employee updateProfile(String email, UpdateEmployeeProfileDTO dto) {
//        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
//        if (optionalEmployee.isEmpty()) {
//            throw new RuntimeException("Employee not found");
//        }
//
//        Employee employee = optionalEmployee.get();
//
//        // Update fields from DTO
//        employee.setFirstName(dto.getFirstName());
//        employee.setLastName(dto.getLastName());
//        employee.setUsername(dto.getUsername());
//        employee.setPhone(dto.getPhone());
//        employee.setGender(dto.getGender());
//        employee.setDob(dto.getDob());
//        employee.setAddress(dto.getAddress());
//        employee.setCity(dto.getCity());
//        employee.setBloodGroup(dto.getBloodGroup());
//        employee.setEmergencyNumber(dto.getEmergencyNumber());
//        employee.setLanguagesKnown(dto.getLanguagesKnown());
//        employee.setMaritalStatus(dto.getMaritalStatus());
//        employee.setResumeFilePath(dto.getResumeFilePath());
//        employee.setOfferLetterFilePath(dto.getOfferLetterFilePath());
//        employee.setJoiningLetterFilePath(dto.getJoiningLetterFilePath());
//
//        return employeeRepository.save(employee);
//    }








    public Employee updateProfile(String email, UpdateEmployeeProfileDTO dto) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if (optionalEmployee.isEmpty()) {
            throw new RuntimeException("Employee not found");
        }

        Employee employee = optionalEmployee.get();

        // Basic Info
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setUsername(dto.getUsername());
        employee.setPhone(dto.getPhone());
        employee.setGender(dto.getGender());
        employee.setDob(dto.getDob());
        employee.setAddress(dto.getAddress());
        employee.setCity(dto.getCity());
        employee.setBloodGroup(dto.getBloodGroup());
        employee.setEmergencyNumber(dto.getEmergencyNumber());
        employee.setLanguagesKnown(dto.getLanguagesKnown());
        employee.setMaritalStatus(dto.getMaritalStatus());
        employee.setResumeFilePath(dto.getResumeFilePath());
        employee.setOfferLetterFilePath(dto.getOfferLetterFilePath());
        employee.setJoiningLetterFilePath(dto.getJoiningLetterFilePath());

        // 🔽 New Company Details
        employee.setEmployeeId(dto.getEmployeeId());
        employee.setDepartment(dto.getDepartment());
        employee.setDesignation(dto.getDesignation());
        employee.setDateOfJoining(dto.getDateOfJoining());
        employee.setDateOfLeaving(LocalDate.parse(dto.getDateOfLeaving()));
        employee.setStatus(dto.getStatus());

        // 🔽 New Bank Account Details
        employee.setBankHolderName(dto.getBankHolderName());
        employee.setBranchName(dto.getBranchName());
        employee.setBankName(dto.getBankName());
        employee.setAccountNumber(dto.getAccountNumber());
        employee.setIfscCode(dto.getIfscCode());
        employee.setPanCardNumber(dto.getPanCardNumber());

        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByUsernameOrEmail(String input) {
        return employeeRepository.findByUsernameOrEmail(input, input).orElse(null);
    }





}

