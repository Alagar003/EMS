package com.example.demo.repositories;

import com.example.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);   // Already present
    Optional<Employee> findByUsername(String username); // ✅ Add this method
    Optional<Employee> findByUsernameOrEmail(String username, String email);

}
