package com.Justice.Justice.Repositories;

import com.Justice.Justice.Models.Avocat;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AvocatRepository extends JpaRepository<Avocat, String> {
    Optional<Avocat> findByAvocatId(String avocatId);
    List<Avocat> findBySpecialite(String specialite);
    Optional<Avocat> findByEmail(String email);
}