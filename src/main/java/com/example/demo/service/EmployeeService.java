package com.example.demo.service;

import com.example.demo.model.Admin;
import com.example.demo.model.Employee;
import com.example.demo.model.Role;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
