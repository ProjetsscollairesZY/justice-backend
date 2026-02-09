package com.Justice.Justice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Justice.Justice.Models.ArchiveAffaire;
import java.util.List;

public interface ArchiveAffaireRepository extends JpaRepository<ArchiveAffaire, Long> {
    List<ArchiveAffaire> findByAffaireId(String affaireId);
    List<ArchiveAffaire> findByAvocatId(String avocatId);
    List<ArchiveAffaire> findByClientId(String clientId);
}