//package com.example.demo.service;
//
//import com.example.demo.model.Admin;
//import com.example.demo.model.Role;
//import com.example.demo.utils.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//import com.example.demo.repositories.AdminRepository;
//@Service
//public class AdminService {
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    public String registerAdmin(Admin admin) {
//        System.out.println("Registering Admin: " + admin.getEmail());
//
//        Optional<Admin> existingByEmail = adminRepository.findByEmail(admin.getEmail());
//        if (existingByEmail.isPresent()) {
//            return "Email already exists!";
//        }
//
//        Optional<Admin> existingByUsername = adminRepository.findByUsername(admin.getUsername());
//        if (existingByUsername.isPresent()) {
//            return "Username already exists!";
//        }
//
//        adminRepository.save(admin);
//        return "Admin registered successfully!";
//    }
//
//
//
//    public String loginAdmin(String input, String password) {
//        Optional<Admin> adminOpt = adminRepository.findByEmail(input);
//        if (adminOpt.isEmpty()) {
//            adminOpt = adminRepository.findByUsername(input);
//        }
//
//        if (adminOpt.isPresent()) {
//            Admin admin = adminOpt.get();
//
//            // ✅ Compare passwords directly
//            if (password.equals(admin.getPassword())) {
//                return jwtUtil.generateToken(admin.getUsername(), "ROLE_" + admin.getRole().name());
//
//            }
//        }
//
//        return null; // ❌ Invalid credentials
//    }
//
//}




package com.example.demo.service;

import com.example.demo.model.Admin;
import com.example.demo.model.Employee;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public String registerAdmin(Admin admin) {
        System.out.println("Registering Admin: " + admin.getEmail());

        Optional<Admin> existingByEmail = adminRepository.findByEmail(admin.getEmail());
        if (existingByEmail.isPresent()) {
            return "Email already exists!";
        }

        Optional<Admin> existingByUsername = adminRepository.findByUsername(admin.getUsername());
        if (existingByUsername.isPresent()) {
            return "Username already exists!";
        }

        adminRepository.save(admin);
        return "Admin registered successfully!";
    }

//    public String loginAdmin(String input, String password) {
//        Optional<Admin> adminOpt = adminRepository.findByEmail(input);
//        if (adminOpt.isEmpty()) {
//            adminOpt = adminRepository.findByUsername(input);
//        }
//
//        if (adminOpt.isPresent()) {
//            Admin admin = adminOpt.get();
//            if (password.equals(admin.getPassword())) {
//                return jwtUtil.generateToken(admin.getUsername(), "ROLE_" + admin.getRole().name());
//            }
//        }
//        return null;
//    }


    public String loginAdmin(String input, String password) {
        Optional<Admin> adminOpt = adminRepository.findByEmail(input);
        if (adminOpt.isEmpty()) {
            adminOpt = adminRepository.findByUsername(input);
        }

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (password.equals(admin.getPassword())) {
                logger.info("Admin {} logged in successfully", admin.getUsername());
                return jwtUtil.generateToken(admin.getUsername(), "ROLE_" + admin.getRole().name());
            } else {
                logger.warn("Incorrect password for admin: {}", admin.getUsername());
            }
        } else {
            logger.warn("Admin with username/email {} does not exist", input);
        }
        return null;
    }


}

