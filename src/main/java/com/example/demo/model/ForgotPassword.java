package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fpid;


    @Column(nullable = false)
    private Integer otp;

    @Column(nullable = false)
    private Date expirationTime;

//    @OneToOne
//    private Admin admin;

    @OneToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    //    @OneToOne
//    private Employee employee;
//@OneToOne
//@JoinColumn(name = "employee_id")
//private Employee employee;
    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonManagedReference
    private Employee employee;




}
