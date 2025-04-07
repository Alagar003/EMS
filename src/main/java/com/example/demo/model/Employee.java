//package com.example.demo.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "EMPLOYEE")
//public class Employee {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    private String username;
//
//    private String password; // Store hashed password
//
//    private String role = "EMPLOYEE"; // Default role
//
//}


//
//package com.example.demo.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "EMPLOYEE")
//public class Employee {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    @Column(unique = true, nullable = false)
//    private String username;
//
//    @Column(nullable = false)
//    private String password;
//
//
//    @OneToOne(mappedBy = "employee")
//    private ForgotPassword forgotPassword;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role = Role.EMPLOYEE; // Default role is EMPLOYEE
//}


package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.EMPLOYEE;

//    @OneToOne(mappedBy = "employee")
//    private ForgotPassword forgotPassword;

    @OneToOne(mappedBy = "employee")
    @JsonBackReference
    private ForgotPassword forgotPassword;

    // New Profile Fields
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;

    private LocalDate dob;

    private String address;
    private String city;
    private String bloodGroup;
    private String emergencyNumber;
    private String languagesKnown;
    private String maritalStatus;

    private String resumeFilePath;
    private String offerLetterFilePath;
    private String joiningLetterFilePath;


    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "department")
    private String department;

    @Column(name = "designation")
    private String designation;

    @Column(name = "date_of_joining")
    private String dateOfJoining;

//    @Column(name = "date_of_leaving")
//    private String dateOfLeaving;
@Column(name = "date_of_leaving")
private LocalDate dateOfLeaving;

    @Column(name = "status")
    private String status;

    @Column(name = "bank_holder_name")
    private String bankHolderName;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "pan_card_number")
    private String panCardNumber;

}
