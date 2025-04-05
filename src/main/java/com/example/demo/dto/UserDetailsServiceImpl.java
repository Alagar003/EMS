package com.example.demo.dto;

import com.example.demo.model.Admin;
import com.example.demo.model.Employee;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.EmployeeRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;

    public UserDetailsServiceImpl(AdminRepository adminRepository, EmployeeRepository employeeRepository) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username).orElse(null);
        if (admin != null) {
            return new User(admin.getUsername(), admin.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }
        Employee employee = employeeRepository.findByUsername(username).orElse(null);
        if (employee != null) {
            return new User(employee.getUsername(), employee.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE")));
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
