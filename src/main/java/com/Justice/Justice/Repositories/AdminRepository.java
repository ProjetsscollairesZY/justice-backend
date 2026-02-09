package com.Justice.Justice.Repositories;

import com.Justice.Justice.Models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByAdminId(String adminId);  // Changé de findByIdAdmin à findByAdminId
}