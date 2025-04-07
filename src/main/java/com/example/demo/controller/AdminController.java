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
import com.example.demo.dto.UpdateEmployeeProfileDTO;
import com.example.demo.model.Admin;
import com.example.demo.model.Employee;
import com.example.demo.model.Role;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.EmployeeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

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

//
//        @PostMapping("/create-employee")
//        public ResponseEntity<?> createEmployee(@RequestBody UpdateEmployeeProfileDTO updateEmployeeProfileDTO) {
//            try {
//                employeeService.updateProfile(updateEmployeeProfileDTO);
//                return ResponseEntity.ok(Collections.singletonMap("message", "Employee created successfully!"));
//            } catch (IllegalArgumentException e) {
//                return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
//            }
//        }









//    @PostMapping("/employee/login")
//        public ResponseEntity<?> loginEmployee(@RequestBody LoginRequestDTO loginRequest) {
//            if (loginRequest.getUsernameOrEmail() == null || loginRequest.getPassword() == null) {
//                return ResponseEntity.badRequest().body("Missing username/email or password");
//            }
//
//            String token = employeeService.loginEmployee(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
//            if (token != null) {
//                return ResponseEntity.ok(Collections.singletonMap("token", token));
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//            }
//        }


    @PostMapping("/employee/login")
    public ResponseEntity<?> loginEmployee(@RequestBody LoginRequestDTO loginRequest) {
        if (loginRequest.getUsernameOrEmail() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Missing username/email or password");
        }

        // Authenticate and get token
        String token = employeeService.loginEmployee(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());

        if (token != null) {
            // ✅ Fetch the Employee object
            Employee employee = employeeService.getEmployeeByUsernameOrEmail(loginRequest.getUsernameOrEmail());
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }

            // ✅ Send both token and employeeId to frontend
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("employeeId", employee.getId());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }










    @PostMapping("/update-{email}")
        public ResponseEntity<?> updateEmployee(
                @PathVariable String email,
                @ModelAttribute UpdateEmployeeProfileDTO dto,
                @RequestParam(value = "resumeFile", required = false) MultipartFile resumeFile,
                @RequestParam(value = "offerLetterFile", required = false) MultipartFile offerLetterFile,
                @RequestParam(value = "joiningLetterFile", required = false) MultipartFile joiningLetterFile
        ) {
            try {
                if (resumeFile != null && !resumeFile.isEmpty()) {
                    String resumePath = saveFile(resumeFile, email);
                    dto.setResumeFilePath(resumePath);
                }

                if (offerLetterFile != null && !offerLetterFile.isEmpty()) {
                    String offerPath = saveFile(offerLetterFile, email);
                    dto.setOfferLetterFilePath(offerPath);
                }

                if (joiningLetterFile != null && !joiningLetterFile.isEmpty()) {
                    String joiningPath = saveFile(joiningLetterFile, email);
                    dto.setJoiningLetterFilePath(joiningPath);
                }

                employeeService.updateProfile(email, dto);
                return ResponseEntity.ok(Map.of("message", "Employee updated successfully!"));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
            }
        }

        // 🔽 Put this method inside the same class
        private String saveFile(MultipartFile file, String email) throws IOException {
            String uploadDir = "uploads/" + email; // You can customize path
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            return filePath.toString(); // Or return just the relative path if needed
        }

    @GetMapping("/allemployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    }

