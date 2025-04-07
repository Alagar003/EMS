package com.example.demo.controller;

import com.example.demo.model.Leave;
import com.example.demo.model.LeaveStatus;
import com.example.demo.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// LeaveController.java
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    // ✅ Apply for leave
    @PostMapping("/apply")
    public ResponseEntity<Leave> applyLeave(@RequestBody Leave leave) {
        Leave saved = leaveService.applyLeave(leave);
        return ResponseEntity.ok(saved);
    }

    // ✅ Get leaves by employee
    @GetMapping("/employee/{empId}")
    public ResponseEntity<List<Leave>> getLeavesByEmployee(@PathVariable Long empId) {
        return ResponseEntity.ok(leaveService.getLeavesByEmployeeId(empId));
    }

    // ✅ Admin: Get all leaves
    @GetMapping("/all")
    public ResponseEntity<List<Leave>> getAllLeaves() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }

//    // ✅ Admin: Approve/Reject
//    @PutMapping("/{id}/status")
//    public ResponseEntity<Leave> updateStatus(@PathVariable Long id, @RequestParam String status) {
//        Leave updated = leaveService.updateLeaveStatus(id, status.toUpperCase());
//        return ResponseEntity.ok(updated);
//    }

    @PutMapping("/leave/{leaveId}/status")
    public ResponseEntity<?> updateLeaveStatus(@PathVariable Long leaveId,
                                               @RequestParam LeaveStatus status) {
        leaveService.updateLeaveStatus(leaveId, status);
        return ResponseEntity.ok("Leave status updated to " + status);
    }
}





