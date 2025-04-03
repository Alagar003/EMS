// EmployeeRepository.java (Repository Interface)
package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
