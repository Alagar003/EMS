////package com.example.demo.controller;
////
////
////import com.example.demo.dto.MailBody;
////import com.example.demo.dto.ChangePassword;
////import com.example.demo.model.Admin;
////import com.example.demo.model.Employee;
////import com.example.demo.model.ForgotPassword;
////import com.example.demo.repositories.AdminRepository;
////import com.example.demo.repositories.EmployeeRepository;
////import com.example.demo.repositories.ForgotPasswordRepository;
////import com.example.demo.service.EmailService;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.security.core.userdetails.UsernameNotFoundException;
////import org.springframework.web.bind.annotation.*;
////
////import java.time.Instant;
////import java.util.Date;
////import java.util.Objects;
////import java.util.Random;
////
//////@RestController
//////@RequestMapping("/forgotPassword")
//////public class ForgotPasswordController {
//////
//////    private final AdminRepository adminRepository;
//////
//////    private final EmployeeRepository employeeRepository;
//////
//////    private final EmailService emailService;
//////
//////    private final ForgotPasswordRepository forgotPasswordRepository;;
//////
//////    public ForgotPasswordController(AdminRepository adminRepository, EmployeeRepository employeeRepository, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository) {
//////        this.adminRepository = adminRepository;
//////        this.employeeRepository = employeeRepository;
//////        this.emailService = emailService;
//////        this.forgotPasswordRepository = forgotPasswordRepository;
//////    }
//////
//////    @PostMapping("/veifymail/{email}")
//////    public ResponseEntity<String> veifyMail(@PathVariable String email) {
//////        Admin admin = adminRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Please provide vail email"));
//////        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Please provide vail email"));
//////
//////        int otp = otpGenerator();
//////        MailBody mailBody = MailBody.builder()
//////                .to(email)
//////                .text("This is the OTP for your forgot password Request:" + otp)
//////                .subject("Otp for Forgot password request")
//////                .build();
//////
//////        ForgotPassword fp = ForgotPassword.builder()
//////                .otp(otp)
//////                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
//////                .admin(admin)
//////                .employee(employee)
//////                .build();
//////
//////        emailService.sendSimpleMail(mailBody);
//////        forgotPasswordRepository.save(fp);
//////
//////        return ResponseEntity.ok("OTP sent for Verification");
//////
//////    }
//////
//////    @PostMapping("/verifyotp/{otp}/{email}")
//////    public  ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
//////        Admin admin = adminRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Please provide vail email"));
//////        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Please provide valid email"));
//////
//////        ForgotPassword fp = forgotPasswordRepository.findByOtpAAndAdmin(otp,admin)
//////                .orElseThrow(() -> new UsernameNotFoundException("Please provide valid OTP"));
//////
//////        ForgotPassword fp = forgotPasswordRepository.findByOtpAAndEmployee(otp,employee)
//////                .orElseThrow(() -> new UsernameNotFoundException("Please provide valid OTP"));
//////
//////        if(fp.getExpirationTime().before(Date.from(Instant.now()))){
//////            forgotPasswordRepository.deleteById(fp.getFpid());
//////            return new ResponseEntity<>("OTP has Expired", HttpStatus.EXPECTATION_FAILED)
//////        }return ResponseEntity.ok("OTP Verified");
//////
//////    }
//////
//////
//////    @PostMapping("/chnagePassword/{email}")
//////    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword, @PathVariable String email) {
//////       if(!Objects.equals(changePassword.password(),changePassword.repeatPassword())){
//////           return new ResponseEntity<>("Wrong password", HttpStatus.EXPECTATION_FAILED);
//////       }
//////       String finalpasswword = changePassword.password();
//////       adminRepository.updatePassword(email,finalpasswword);
//////       employeeRepository.updatePassword(email,finalpasswword);
//////
//////       return ResponseEntity.ok("Password changed");
//////    }
//////    private Integer otpGenerator(){
//////        Random random = new Random();
//////        return  random.nextInt(100_000, 999_999)
//////    }
//////}
//
//
//@RestController
//@RequestMapping("/forgotPassword")
//public class ForgotPasswordController {
//
//    private final AdminRepository adminRepository;
//    private final EmployeeRepository employeeRepository;
//    private final EmailService emailService;
//    private final ForgotPasswordRepository forgotPasswordRepository;
//
//    public ForgotPasswordController(AdminRepository adminRepository, EmployeeRepository employeeRepository,
//                                    EmailService emailService, ForgotPasswordRepository forgotPasswordRepository) {
//        this.adminRepository = adminRepository;
//        this.employeeRepository = employeeRepository;
//        this.emailService = emailService;
//        this.forgotPasswordRepository = forgotPasswordRepository;
//    }
//
//    @PostMapping("/verifymail/{email}")
//    public ResponseEntity<String> verifyMail(@PathVariable String email) {
//        Admin admin = adminRepository.findByEmail(email).orElse(null);
//        Employee employee = employeeRepository.findByEmail(email).orElse(null);
//
//        if (admin == null && employee == null) {
//            return new ResponseEntity<>("Please provide a valid email", HttpStatus.NOT_FOUND);
//        }
//
//        int otp = otpGenerator();
//
//        MailBody mailBody = MailBody.builder()
//                .to(email)
//                .subject("OTP for Forgot Password Request")
//                .text("Your OTP is: " + otp)
//                .build();
//
//        ForgotPassword forgotPassword = ForgotPassword.builder()
//                .otp(otp)
//                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
//                .admin(admin)
//                .employee(employee)
//                .build();
//
//        emailService.sendSimpleMail(mailBody);
//        forgotPasswordRepository.save(forgotPassword);
//
//        return ResponseEntity.ok("OTP sent successfully to email.");
//    }
//
//    @PostMapping("/verifyotp/{otp}/{email}")
//    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
//        Admin admin = adminRepository.findByEmail(email).orElse(null);
//        Employee employee = employeeRepository.findByEmail(email).orElse(null);
//
//        ForgotPassword fp = null;
//
//        if (admin != null) {
//            fp = forgotPasswordRepository.findByOtpAndAdmin(otp, admin).orElse(null);
//        } else if (employee != null) {
//            fp = forgotPasswordRepository.findByOtpAndEmployee(otp, employee).orElse(null);
//        }
//
//        if (fp == null) {
//            return new ResponseEntity<>("Invalid OTP", HttpStatus.NOT_FOUND);
//        }
//
//        if (fp.getExpirationTime().before(new Date())) {
//            forgotPasswordRepository.deleteById(fp.getFpid());
//            return new ResponseEntity<>("OTP has expired", HttpStatus.EXPECTATION_FAILED);
//        }
//
//        return ResponseEntity.ok("OTP verified successfully.");
//    }
//
//    @PostMapping("/changepassword/{email}")
//    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword, @PathVariable String email) {
//        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
//            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
//        }
//
//        Admin admin = adminRepository.findByEmail(email).orElse(null);
//        Employee employee = employeeRepository.findByEmail(email).orElse(null);
//
//        if (admin == null && employee == null) {
//            return new ResponseEntity<>("Invalid email", HttpStatus.NOT_FOUND);
//        }
//
//        String finalPassword = changePassword.password();
//
//        if (admin != null) {
//            adminRepository.updatePassword(email, finalPassword);
//        }
//
//        if (employee != null) {
//            employeeRepository.updatePassword(email, finalPassword);
//        }
//
//        return ResponseEntity.ok("Password changed successfully.");
//    }
//
//    private Integer otpGenerator() {
//        return new Random().nextInt(900_000) + 100_000;
//    }
//}



package com.example.demo.controller;

import com.example.demo.dto.MailBody;
import com.example.demo.dto.ChangePassword;
import com.example.demo.model.Admin;
import com.example.demo.model.Employee;
import com.example.demo.model.ForgotPassword;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.ForgotPasswordRepository;
import com.example.demo.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;

    public ForgotPasswordController(AdminRepository adminRepository, EmployeeRepository employeeRepository, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

//    @PostMapping("/verifyMail/{email}")
//    public ResponseEntity<String> verifyMail(@PathVariable String email) {
//        Admin admin = adminRepository.findByEmail(email).orElse(null);
//        Employee employee = employeeRepository.findByEmail(email).orElse(null);
//
//        if (admin == null && employee == null) {
//            throw new UsernameNotFoundException("Please provide a valid email");
//        }
//
//        int otp = otpGenerator();
//        MailBody mailBody = MailBody.builder()
//                .to(email)
//                .text("This is the OTP for your forgot password request: " + otp)
//                .subject("OTP for Forgot Password Request")
//                .build();
//
//        ForgotPassword fp = ForgotPassword.builder()
//                .otp(otp)
//                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
//                .admin(admin)
//                .employee(employee)
//                .build();
//
//        emailService.sendSimpleMail(mailBody);
//        forgotPasswordRepository.save(fp);
//
//        return ResponseEntity.ok("OTP sent for verification");
//    }





    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyMail(@PathVariable String email) {
        // Check if the email belongs to an Admin or Employee
        Admin admin = adminRepository.findByEmail(email).orElse(null);
        Employee employee = employeeRepository.findByEmail(email).orElse(null);

        if (admin == null && employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please provide a valid email");
        }

        // Check for existing OTP entry
        Optional<ForgotPassword> existingEntryOpt = forgotPasswordRepository.findByAdminIdOrEmployeeId(
                admin != null ? admin.getId() : null,
                employee != null ? employee.getId() : null
        );

        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your forgot password request: " + otp)
                .subject("OTP for Forgot Password Request")
                .build();

        if (existingEntryOpt.isPresent()) {
            // Update the existing entry with a new OTP and expiration time
            ForgotPassword existingEntry = existingEntryOpt.get();
            existingEntry.setOtp(otp);
            existingEntry.setExpirationTime(new Date(System.currentTimeMillis() + 70 * 1000));
            forgotPasswordRepository.save(existingEntry);
        } else {
            // Create a new entry if none exists
            ForgotPassword fp = ForgotPassword.builder()
                    .otp(otp)
                    .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                    .admin(admin)
                    .employee(employee)
                    .build();
            forgotPasswordRepository.save(fp);
        }

        // Send the email with the OTP
        emailService.sendSimpleMail(mailBody);

        return ResponseEntity.ok("OTP sent for verification");
    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        Admin admin = adminRepository.findByEmail(email).orElse(null);
        Employee employee = employeeRepository.findByEmail(email).orElse(null);

        if (admin == null && employee == null) {
            throw new UsernameNotFoundException("Please provide a valid email");
        }

        ForgotPassword fp = null;
        if (admin != null) {
            fp = forgotPasswordRepository.findByOtpAAndAdmin(otp, admin).orElse(null);
        }
        if (employee != null) {
            fp = forgotPasswordRepository.findByOtpAAndEmployee(otp, employee).orElse(fp);
        }

        if (fp == null) {
            throw new UsernameNotFoundException("Please provide a valid OTP");
        }

        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP has expired", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP verified");
    }

//    @PostMapping("/changePassword/{email}")
//    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword, @PathVariable String email) {
//        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
//            return new ResponseEntity<>("Passwords do not match", HttpStatus.EXPECTATION_FAILED);
//        }
//
//        String finalPassword = changePassword.password();
//        adminRepository.updatePassword(email, finalPassword);
//        employeeRepository.updatePassword(email, finalPassword);
//
//        return ResponseEntity.ok("Password changed successfully");
//    }



    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}
