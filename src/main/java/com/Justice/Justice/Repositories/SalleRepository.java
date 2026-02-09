package com.Justice.Justice.Repositories;

import com.Justice.Justice.Models.Salle;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface SalleRepository extends JpaRepository<Salle, String> {
    
    Optional<Salle> findBySalleId(String salleId);
    
}
