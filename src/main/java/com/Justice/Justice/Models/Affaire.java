package com.Justice.Justice.Models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "affaires")
public class Affaire {
    @Id
    @Column(name = "affaire_id")
    private String affaireId;  // Changé de idAffaire à affaireId

    @Column(name = "type_affaire")
    private String typeAffaire;

    @Column(name = "description")
    private String description;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "statut")
    private String statut;

    @Column(name = "tribunal_id")
    private String tribunalId;

    @Column(name = "avocat_id")
    private String avocatId;

    @Column(name = "secretaire_id")
    private String secretaireId;

    @Column(name = "date_jugement")
    private LocalDate dateJugement;
    
    @Column(name = "client_id")
    private String clientId;





    public Affaire() {}

    public Affaire(String affaireId, String typeAffaire, String description, LocalDate dateCreation, String statut, String tribunalId, String avocatId, String clientId,LocalDate dateJugement) {
        this.affaireId = affaireId;
        this.typeAffaire = typeAffaire;
        this.description = description;
        this.dateCreation = dateCreation;
        this.statut = statut;
        this.tribunalId = tribunalId;
        this.avocatId = avocatId;
        this.clientId = clientId;
        this.dateJugement = dateJugement;
    }


    

    public String getAffaireId() { return affaireId; }
    public void setAffaireId(String idAffaire, String affaireId) { this.affaireId = affaireId; }

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
    public LocalDate getDateJugement() { return dateJugement; }
    public void setDateJugement(LocalDate dateJugement) { this.dateJugement = dateJugement; }

}

