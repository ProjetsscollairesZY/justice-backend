package com.Justice.Justice.Repositories;

import com.Justice.Justice.Models.Juge;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JugeRepository extends JpaRepository<Juge, String> {
    Optional<Juge> findByJugeId(String jugeId);
    List<Juge> findByTribunalId(String tribunalId);
    List<Juge> findBySpecialite(String specialite);
}