package com.example.demo.service;//package com.example.demo.service;
//
//import com.example.demo.model.Leave;
//import com.example.demo.repositories.LeaveRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//// LeaveService.java
//public interface LeaveService {
//    List<Leave> getLeavesByEmployeeId(Long employeeId);
//    Leave applyLeave(Leave leave);
//    List<Leave> getAllLeaves();
//}
//


import com.example.demo.model.Leave;
import com.example.demo.model.LeaveStatus;
import com.example.demo.repositories.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {
    @Autowired
    private LeaveRepository leaveRepo;

    public Leave applyLeave(Leave leave) {
        return leaveRepo.save(leave);
    }

    public List<Leave> getLeavesByEmployeeId(Long empId) {
        return leaveRepo.findByEmployeeId(empId);
    }

    public List<Leave> getAllLeaves() {
        return leaveRepo.findAll();
    }

    public Leave updateLeaveStatus(Long id, String status) {
        Leave leave = leaveRepo.findById(id).orElseThrow();
        leave.setStatus(LeaveStatus.valueOf(status));
        return leaveRepo.save(leave);
    }

    public void updateLeaveStatus(Long leaveId, LeaveStatus status) {
        Leave leave = leaveRepo.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus(status);
        leaveRepo.save(leave);
    }

}
