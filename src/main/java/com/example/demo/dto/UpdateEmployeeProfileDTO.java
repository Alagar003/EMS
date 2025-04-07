package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class UpdateEmployeeProfileDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
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


    private String employeeId;
    private String department;
    private String designation;
    private String dateOfJoining;
    private String dateOfLeaving;
    private String status; // eg. "Currently Working"

    // Bank Details
    private String bankHolderName;
    private String branchName;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String panCardNumber;

}
