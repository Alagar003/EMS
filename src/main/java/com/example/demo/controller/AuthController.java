//package com.example.demo.controller;
//
//import com.example.demo.dto.LoginRequest;
//import com.example.demo.model.Employee;
//import com.example.demo.repositories.EmployeeRepository;
//import com.example.demo.service.EmployeeService;
//import com.example.demo.utils.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestBody;
//import java.util.Collections;
//import java.util.Map;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    EmployeeService employeeService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @PostMapping("/employee/login")
//    public ResponseEntity<?> loginEmployee(@RequestBody Map<String, String> loginRequest) {
//        String input = loginRequest.get("input");
//        String password = loginRequest.get("password");
//
//        if (input == null || password == null) {
//            return ResponseEntity.badRequest().body("Missing username/email or password");
//        }
//
//        String token = employeeService.loginEmployee(input, password);
//        if (token != null) {
//            return ResponseEntity.ok(Collections.singletonMap("token", token));
//        } else {
//            return ResponseEntity.status(401).body("Invalid credentials");
//        }
//    }
//
//
//}



package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employee/login")
    public ResponseEntity<?> loginEmployee(@RequestBody Map<String, String> loginRequest) {
        String input = loginRequest.get("input");
        String password = loginRequest.get("password");

        if (input == null || password == null) {
            return ResponseEntity.badRequest().body("Missing username/email or password");
        }

        String token = employeeService.loginEmployee(input, password);
        if (token != null) {
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
