package com.Justice.Justice.Repositories;

import com.Justice.Justice.Models.Tribunal;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TribunalRepository extends JpaRepository<Tribunal, String> {
    Optional<Tribunal> findByTribunalId(String idTribunal);
}