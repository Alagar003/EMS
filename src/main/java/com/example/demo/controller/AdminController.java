package com.example.demo.controller;

import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin) {
        String response = adminService.registerAdmin(admin);

        if (response.contains("exists")) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", response));
        }

        return ResponseEntity.ok(Collections.singletonMap("message", response));
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> loginRequest) {
        String input = loginRequest.get("input");
        String password = loginRequest.get("password");

        if (input == null || password == null) {
            return ResponseEntity.badRequest().body("Missing username/email or password");
        }

        String token = adminService.loginAdmin(input, password);
        if (token != null) {
            return ResponseEntity.ok(Collections.singletonMap("token", token)); // <-- Updated line
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}