package com.Justice.Justice.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "tribunals")
public class Tribunal {
    @Id
    @Column(name = "tribunal_id", unique = true)
    private String tribunalId;
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "willaya")
    private String willaya;

    @Column(name = "commune")
    private String commune;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "type")
    private String type;


   
    public Tribunal() {}
    
    public Tribunal(String nom, String willaya,String commune, String adresse, String type) {
        this.nom = nom;
        this.willaya = willaya;
        this.commune = commune;
        this.adresse = adresse;
        this.type = type;
    }
    

    public String getTribunalId() { return tribunalId; }
   public void setTribunalId(String tribunalId) { this.tribunalId = tribunalId; }
  
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getWillaya() { return willaya; }
    public void setWillaya(String willaya) { this.willaya = willaya; }
    
    public String getCommune() { return commune; }
    public void setCommune(String commune) { this.commune = commune; }
    
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}