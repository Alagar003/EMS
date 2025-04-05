package com.example.demo.repositories;

import com.example.demo.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByUsername(String username); // ✅ Add this method


    @Transactional
    @Modifying
    @Query("update Admin u set u.password = ?2 where u.email = ?1")
    void updatePassword(String email, String password);
}
