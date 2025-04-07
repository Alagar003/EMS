package com.example.demo.service;

import com.example.demo.model.Attendance;
import com.example.demo.model.Employee;
import com.example.demo.repositories.AttendanceRepository;
import com.example.demo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public Attendance checkIn(Long empId) {
        LocalDate today = LocalDate.now();

        if (attendanceRepo.findByEmployeeIdAndDate(empId, today).isPresent()) {
            throw new RuntimeException("Already checked in today");
        }

        Employee employee = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(today);
        attendance.setCheckIn(LocalTime.now());
        attendance.setStatus("Present");

        return attendanceRepo.save(attendance);
    }

    public Attendance checkOut(Long empId) {
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepo.findByEmployeeIdAndDate(empId, today)
                .orElseThrow(() -> new RuntimeException("No check-in found"));

        if (attendance.getCheckOut() != null) {
            throw new RuntimeException("Already checked out");
        }

        attendance.setCheckOut(LocalTime.now());
        return attendanceRepo.save(attendance);
    }

    public List<Attendance> getAllByEmployee(Long empId) {
        return attendanceRepo.findByEmployeeId(empId);
    }
}
