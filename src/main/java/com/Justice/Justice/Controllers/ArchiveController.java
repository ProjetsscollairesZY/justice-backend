package com.Justice.Justice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.Justice.Justice.Models.Affaire;
import com.Justice.Justice.Models.ArchiveAffaire;
import com.Justice.Justice.Repositories.AffaireRepository;
import com.Justice.Justice.Repositories.ArchiveAffaireRepository;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/archives")
public class ArchiveController {

    @Autowired
    private AffaireRepository affaireRepository;
    
    @Autowired
    private ArchiveAffaireRepository archiveAffaireRepository;

    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void archiverAutomatiquement() {
        LocalDate dateLimite = LocalDate.now().minusYears(1);
        
        List<Affaire> affairesAArchiver = affaireRepository.findByDateCreationBefore(dateLimite);
        
        if (!affairesAArchiver.isEmpty()) {
            for (Affaire affaire : affairesAArchiver) {
                ArchiveAffaire archive = new ArchiveAffaire(affaire, null);
                archiveAffaireRepository.save(archive);
                affaireRepository.delete(affaire);
            }
            System.out.println("✅ Archivage auto: " + affairesAArchiver.size() + " affaires (>1 an)");
        }
    }

   
    @PostMapping("/manuel/{id}")
    @Transactional
    public String archiverManuellement(@PathVariable String id) {
        return affaireRepository.findById(id)
            .map(affaire -> {
                // Créer l'archive
                ArchiveAffaire archive = new ArchiveAffaire(affaire, id);
                archiveAffaireRepository.save(archive);
                
                // Supprimer de la table principale
                affaireRepository.delete(affaire);
                
                return "✅ Affaire '" + id + "' archivée manuellement";
            })
            .orElse("❌ Affaire '" + id + "' non trouvée");
    }
    


   
    @GetMapping("/all")
    public List<ArchiveAffaire> voirArchives() {
        return archiveAffaireRepository.findAll();
    }
}