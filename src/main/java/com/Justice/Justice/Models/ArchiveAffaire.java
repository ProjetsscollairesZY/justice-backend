package com.Justice.Justice.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "archives_affaires")
public class ArchiveAffaire {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archive_id")
    private Long archiveId; 

    @Column(name = "affaire_id")
    private String affaireId;
    
    @Column(name = "type_affaire")
    private String typeAffaire;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "date_creation")
    private LocalDate dateCreation;
    
    @Column(name = "statut")
    private String statut;
    
    @Column(name = "tribunal_id")
    private String tribunalId;
    
    @Column(name = "avocat_id")
    private String avocatId;
    
    @Column(name = "client_id")
    private String clientId;
    
    @Column(name = "date_archive")
    private LocalDateTime dateArchive;
    
 
    // Constructeurs
    public ArchiveAffaire() {
        this.dateArchive = LocalDateTime.now();
    }
    
    public ArchiveAffaire(Affaire affaire, String raison) {
        this.affaireId = affaire.getAffaireId();
        this.typeAffaire = affaire.getTypeAffaire();
        this.description = affaire.getDescription();
        this.dateCreation = affaire.getDateCreation();
        this.statut = affaire.getStatut();
        this.tribunalId = affaire.getTribunalId();
        this.avocatId = affaire.getAvocatId();
  
        this.clientId = affaire.getClientId();
        this.dateArchive = LocalDateTime.now();
    }
    
    // Getters et Setters
    public Long getArchive_Id() { return archiveId; }
    public void setArchive_Id(Long archive_Id) { this.archiveId = archive_Id; }
    
    public String getAffaireId() { return affaireId; }
    public void setAffaireId(String affaireId) { this.affaireId = affaireId; }
    
    public String getTypeAffaire() { return typeAffaire; }
    public void setTypeAffaire(String typeAffaire) { this.typeAffaire = typeAffaire; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public String getTribunalId() { return tribunalId; }
    public void setTribunalId(String tribunalId) { this.tribunalId = tribunalId; }
    
    public String getAvocatId() { return avocatId; }
    public void setAvocatId(String avocatId) { this.avocatId = avocatId; }
    

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    
    public LocalDateTime getDateArchive() { return dateArchive; }
    public void setDateArchive(LocalDateTime dateArchive) { this.dateArchive = dateArchive; }
  
}