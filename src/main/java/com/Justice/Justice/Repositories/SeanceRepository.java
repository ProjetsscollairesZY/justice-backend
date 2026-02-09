package com.Justice.Justice.Repositories;

import com.Justice.Justice.Models.Seance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SeanceRepository extends JpaRepository<Seance, String> {

    List<Seance> findByAffaireId(String affaireId);
    List<Seance> findByJugeId(String jugeId);
    List<Seance> findByAvocatId(String avocatId);
    List<Seance> findByStatut(String statut);
    Optional<Seance> findBySeanceId(String idSeance);
        List<Seance> findByStatutIn(List<String> of);

    
}