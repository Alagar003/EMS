//package com.example.demo.controller;
//
//import com.example.demo.model.Admin;
//import com.example.demo.model.Employee;
//import com.example.demo.model.Role;
//import com.example.demo.repositories.EmployeeRepository;
//import com.example.demo.service.AdminService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collections;
//import java.util.Map;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/admin")
//public class AdminController {
//    @Autowired
//    private AdminService adminService;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @PostMapping("/register")
//    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin) {
//        System.out.println("Received Admin Registration Request: " + admin);
//
//        String response = adminService.registerAdmin(admin);
//
//        if (response.contains("exists")) {
//            return ResponseEntity.badRequest().body(Collections.singletonMap("message", response));
//        }
//
//        return ResponseEntity.ok(Collections.singletonMap("message", response));
//    }
//
//
//
//    @PostMapping("/login")
//    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> loginRequest) {
//        String input = loginRequest.get("input");
//        String password = loginRequest.get("password");
//
//        if (input == null || password == null) {
//            return ResponseEntity.badRequest().body("Missing username/email or password");
//        }
//
//        String token = adminService.loginAdmin(input, password);
//        if (token != null) {
//            return ResponseEntity.ok(Collections.singletonMap("token", token)); // <-- Updated line
//        } else {
//            return ResponseEntity.status(401).body("Invalid credentials");
//        }
//    }
//
//    @PostMapping("/create-employee")
//    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
//        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
//            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email already exists!"));
//        }
//
//        employee.setRole(Role.EMPLOYEE); // ✅ Ensure role is assigned correctly
//
//        employeeRepository.save(employee);
//        return ResponseEntity.ok(Collections.singletonMap("message", "Employee created successfully!"));
//    }
//
//
//}



package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.model.Admin;
import com.example.demo.model.Employee;
import com.example.demo.model.Role;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin) {
        String response = adminService.registerAdmin(admin);
        if (response.contains("exists")) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", response));
        }
        return ResponseEntity.ok(Collections.singletonMap("message", response));
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> LoginRequestDTO) {
//        String input = LoginRequestDTO.get("usernameOrEmail");
//        String password = LoginRequestDTO.get("password");
//
//        if (input == null || password == null) {
//            return ResponseEntity.badRequest().body("Missing username/email or password");
//        }
//
//        String token = adminService.loginAdmin(input, password);
//        if (token != null) {
//            return ResponseEntity.ok(Collections.singletonMap("token", token)); // <-- Updated line
//        } else {
//            return ResponseEntity.status(401).body("Invalid credentials");
//        }
//    }


    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> LoginRequestDTO) {
        String input = LoginRequestDTO.get("usernameOrEmail");
        String password = LoginRequestDTO.get("password");

        if (input == null || password == null) {
            logger.warn("Login attempt with missing username/email or password");
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Missing username/email or password"));
        }

        String token = adminService.loginAdmin(input, password);
        if (token != null) {
            logger.info("Admin logged in successfully: {}", input);
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            logger.warn("Invalid login attempt for username/email: {}", input);
            return ResponseEntity.status(401).body(Collections.singletonMap("message", "Invalid credentials"));
        }
    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // ✅ Use hasAuthority()
//    @PostMapping("/create-employee")
//    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
//        return employeeService.createEmployee(employee);
//    }
//

//    @PostMapping("/create-employee")
//    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
//        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
//            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email already exists!"));
//        }
//
//        if (employeeRepository.findByUsername(employee.getUsername()).isPresent()) {
//            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Username already exists!"));
//        }
//
//        employee.setRole(Role.EMPLOYEE); // ✅ Set role to EMPLOYEE
//        employeeRepository.save(employee);
//
//        return ResponseEntity.ok(Collections.singletonMap("message", "Employee created successfully!"));
//    }





    //    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // ✅ Use hasAuthority()
//    @PostMapping("/create-employee")
//    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
//        return employeeService.createEmployee(employee);
//    }
//

    @PostMapping("/create-employee")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email already exists!"));
        }

        if (employeeRepository.findByUsername(employee.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Username already exists!"));
        }

        employee.setRole(Role.EMPLOYEE); // ✅ Set role to EMPLOYEE
        employeeRepository.save(employee);

        return ResponseEntity.ok(Collections.singletonMap("message", "Employee created successfully!"));
    }









    @PostMapping("/employee/login")
        public ResponseEntity<?> loginEmployee(@RequestBody LoginRequestDTO loginRequest) {
            if (loginRequest.getUsernameOrEmail() == null || loginRequest.getPassword() == null) {
                return ResponseEntity.badRequest().body("Missing username/email or password");
            }

            String token = employeeService.loginEmployee(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
            if (token != null) {
                return ResponseEntity.ok(Collections.singletonMap("token", token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        }
    }

