package com.example.demo.controller;

import com.example.demo.model.Attendance;
import com.example.demo.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@CrossOrigin("http://localhost:3000")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/checkin/{empId}")
    public ResponseEntity<?> checkIn(@PathVariable Long empId) {
        try {
            return ResponseEntity.ok(attendanceService.checkIn(empId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/checkout/{empId}")
    public ResponseEntity<?> checkOut(@PathVariable Long empId) {
        try {
            return ResponseEntity.ok(attendanceService.checkOut(empId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<List<Attendance>> getAttendance(@PathVariable Long empId) {
        return ResponseEntity.ok(attendanceService.getAllByEmployee(empId));
    }
}

