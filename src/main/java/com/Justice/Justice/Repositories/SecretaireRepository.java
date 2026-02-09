package com.Justice.Justice.Repositories;

import com.Justice.Justice.Models.Secretaire;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SecretaireRepository extends JpaRepository<Secretaire, String> {
    Optional<Secretaire> findBySecretaireId(String secretaireId);
    List<Secretaire> findByTribunalId(String tribunalId);
}