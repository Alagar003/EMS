package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
// Leave.java (Entity)
@Entity
@Data
@Getter
@Setter
@Table(name = "leaves")
public class Leave {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String leaveType;  // e.g., Sick, Casual
 private String reason;

 private LocalDate fromDate;
 private LocalDate toDate;

// private String status = "PENDING"; // PENDING, APPROVED, REJECTED



 @Enumerated(EnumType.STRING)
 private LeaveStatus status = LeaveStatus.PENDING;

 @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
 @ManyToOne(fetch = FetchType.EAGER)
 @JoinColumn(name = "employee_id",nullable = false)
 private Employee employee;

 // ✅ Getters and setters
}
