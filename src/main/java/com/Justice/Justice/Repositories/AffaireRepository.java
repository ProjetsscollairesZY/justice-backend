package com.Justice.Justice.Repositories;

import com.Justice.Justice.Models.Affaire;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AffaireRepository extends JpaRepository<Affaire, String> {
    Optional<Affaire> findByAffaireId(String affaireId);
    List<Affaire> findByTribunalId(String tribunalId);
    List<Affaire> findByAvocatId(String avocatId);
    boolean existsByAffaireIdAndStatutNotIn(String affaireId, List<String> statutsBloquants);
   List<Affaire> findByDateCreationBefore(LocalDate date);
    List<Affaire> findByStatut(String statut);
}