package com.Justice.Justice.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "archives_seances")
public class ArchiveSeance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "archive_id") 

    private Long archiveId;
    
    @Column(name = "seance_id")
    private String seanceId;
    
    @Column(name = "type_seance")
    private String typeSeance;
    
    @Column(name = "statut")
    private String statut;
    
    @Column(name = "affaire_id")
    private String affaireId;
    
    @Column(name = "juge_id")
    private String jugeId;
    
    @Column(name = "avocat_id")
    private String avocatId;
    
    @Column(name = "date_archive")
    private LocalDateTime dateArchive;
    
    // Constructeurs
    public ArchiveSeance() {
        this.dateArchive = LocalDateTime.now();
    }
    
    public ArchiveSeance(Seance seance) {
        this.seanceId = seance.getSeanceId();
        this.typeSeance = seance.getTypeSeance();
        this.statut = seance.getStatut();
        this.affaireId = seance.getAffaireId();
        this.jugeId = seance.getJugeId();
        this.avocatId = seance.getAvocatId();
        this.dateArchive = LocalDateTime.now();
    }

 
    public Long getArchiveId() {    
        return archiveId;
    }
    public void setArchiveId(Long archiveId) {
        this.archiveId = archiveId;
    }
    public String getSeanceId() {
        return seanceId;
    }
    public void setSeanceId(String seanceId) {
        this.seanceId = seanceId;
    }
    public String getTypeSeance() {
        return typeSeance;
    }
    public void setTypeSeance(String typeSeance) {
        this.typeSeance = typeSeance;
    }
    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
    public String getAffaireId() {
        return affaireId;
    }
    public void setAffaireId(String affaireId) {
        this.affaireId = affaireId;
    }
    public String getJugeId() {
        return jugeId;
    }
    public void setJugeId(String jugeId) {
        this.jugeId = jugeId;
    }
    public String getAvocatId() {
        return avocatId;
    }
    public void setAvocatId(String avocatId) {
        this.avocatId = avocatId;
    }
    public LocalDateTime getDateArchive() {
        return dateArchive;
    }
    public void setDateArchive(LocalDateTime dateArchive) {
        this.dateArchive = dateArchive;
    }


}