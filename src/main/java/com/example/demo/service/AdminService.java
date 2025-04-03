package com.example.demo.service;

import com.example.demo.model.Admin;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.demo.repositories.AdminRepository;
@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerAdmin(Admin admin) {
        String email = admin.getEmail().trim();
        String username = admin.getUsername().trim();

        System.out.println("Checking if email exists: " + email);
        System.out.println("Checking if username exists: " + username);

        Optional<Admin> existingByEmail = adminRepository.findByEmail(email);
        Optional<Admin> existingByUsername = adminRepository.findByUsername(username);

        if (existingByEmail.isPresent()) {
            return "Email already exists!";
        }

        if (existingByUsername.isPresent()) {
            return "Username already exists!";
        }

        // Save new admin
        adminRepository.save(admin);
        return "Admin registered successfully!";
    }


    public String loginAdmin(String input, String password) {
        Optional<Admin> adminOpt = adminRepository.findByEmail(input);
        if (adminOpt.isEmpty()) {
            adminOpt = adminRepository.findByUsername(input);
        }

        if (adminOpt.isPresent() && passwordEncoder.matches(password, adminOpt.get().getPassword())) {
            return jwtUtil.generateToken(adminOpt.get().getUsername());
        } else {
            return null;
        }
    }
}
