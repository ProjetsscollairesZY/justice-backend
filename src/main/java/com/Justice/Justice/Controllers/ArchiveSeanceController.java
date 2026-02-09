package com.Justice.Justice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.Justice.Justice.Models.Seance;
import com.Justice.Justice.Models.ArchiveSeance;
import com.Justice.Justice.Repositories.SeanceRepository;
import com.Justice.Justice.Repositories.ArchiveSeanceRepository;
import java.util.List;

@RestController
@RequestMapping("/api/archives-seances")
public class ArchiveSeanceController {

    @Autowired
    private SeanceRepository seanceRepository;
    
    @Autowired
    private ArchiveSeanceRepository archiveSeanceRepository;

    /**
     * FONCTION 1 : Archivage AUTOMATIQUE des séances annulées/terminées
     * S'exécute toutes les 10 minutes
     */
    @Scheduled(cron = "0 */10 * * * ?") // Toutes les 10 minutes
    @Transactional
    public void archiverSeancesAnnuleesTerminees() {
         
List<Seance> seancesAArchiver = seanceRepository.findByStatutIn(List.of("ANNULEE", "TERMINEE"));        
        if (!seancesAArchiver.isEmpty()) {
            for (Seance seance : seancesAArchiver) {
                // Créer l'archive
                ArchiveSeance archive = new ArchiveSeance(seance);
                archiveSeanceRepository.save(archive);
                
                // Supprimer de la table principale
                seanceRepository.delete(seance);
            }
            System.out.println("✅ " + seancesAArchiver.size() + " séances (annulées/terminées) archivées");
        }
    }

   
    @GetMapping("/all")
    public List<ArchiveSeance> voirToutesSeancesArchivees() {
        return archiveSeanceRepository.findAll();
    }
}