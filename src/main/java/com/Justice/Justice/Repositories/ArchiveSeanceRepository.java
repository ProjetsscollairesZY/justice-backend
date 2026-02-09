package com.Justice.Justice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Justice.Justice.Models.ArchiveSeance;
import java.util.List;

public interface ArchiveSeanceRepository extends JpaRepository<ArchiveSeance, Long> {
    List<ArchiveSeance> findBySeanceId(String seanceId);
    List<ArchiveSeance> findByAffaireId(String affaireId);
    List<ArchiveSeance> findByAvocatId(String avocatId);
    List<ArchiveSeance> findByJugeId(String jugeId);
}