package com.Justice.Justice.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "seances")
public class Seance {
    @Id
    @Column(name = "seanceId")
    private String seanceId;  

    @Column(name = "typeSeance")
    private String typeSeance;

    @Column(name = "statut")
    private String statut;

    @Column(name = "affaireId")
    private String affaireId;

    @Column(name = "jugeId")
    private String jugeId;

    @Column(name = "avocatId")
    private String avocatId;

    public Seance() {}

    public Seance(String seanceId, String typeSeance, String statut, String affaireId, String jugeId, String avocatId) {
        this.seanceId = seanceId;
    
        this.typeSeance = typeSeance;
        this.statut = statut;
        this.affaireId = affaireId;
        this.jugeId = jugeId;
        this.avocatId = avocatId;
    }

  
  

    public String getSeanceId() { return seanceId; }
    public void setSeanceId(String seanceId) { this.seanceId = seanceId; }


    public String getTypeSeance() { return typeSeance; }
    public void setTypeSeance(String typeSeance) { this.typeSeance = typeSeance; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public String getAffaireId() { return affaireId; }
    public void setAffaireId(String affaireId) { this.affaireId = affaireId; }
    public String getJugeId() { return jugeId; }
    public void setJugeId(String jugeId) { this.jugeId = jugeId; }
    public String getAvocatId() { return avocatId; }
    public void setAvocatId(String avocatId) { this.avocatId = avocatId; }

}