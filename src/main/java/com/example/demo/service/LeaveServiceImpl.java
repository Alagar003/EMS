//package com.example.demo.service;
//
//import com.example.demo.model.Leave;
//import com.example.demo.repositories.LeaveRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class LeaveServiceImpl implements LeaveService {
//
//    @Autowired
//    private LeaveRepository leaveRepository;
//
//    @Override
//    public List<Leave> getLeavesByEmployeeId(Long employeeId) {
//        return leaveRepository.findByEmployeeId(employeeId);
//    }
//
//    @Override
//    public Leave applyLeave(Leave leave) {
//        return leaveRepository.save(leave);
//    }
//
//    @Override
//    public List<Leave> getAllLeaves() {
//        return leaveRepository.findAll();
//    }
//}
