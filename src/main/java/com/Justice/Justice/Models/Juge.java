package com.Justice.Justice.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "juges")
public class Juge {
    @Id
    @Column(name = "juge_id")
    private String jugeId;  

    @Column(name = "password")
    private String password;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "specialite")
    private String specialite;

    @Column(name = "tribunal_id")
    private String tribunalId;

    @Column(name = "adresse")
    private String adresse;

    // ✅ CONSTRUCTEUR SANS ARGUMENTS REQUIS PAR JPA/HIBERNATE
    public Juge() {
        // Constructeur par défaut nécessaire pour Hibernate
    }

    // Constructeur avec paramètres
    public Juge(String jugeId, String password, String nom, String prenom, String email, String telephone, String specialite, String tribunalId, String adresse) {
        this.jugeId = jugeId;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.specialite = specialite;
        this.tribunalId = tribunalId;
        this.adresse = adresse;
    }

    // Getters et setters...
    public String getJugeId() { return jugeId; }
    public void setJugeId(String jugeId) { this.jugeId = jugeId; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public String getTribunalId() { return tribunalId; }
    public void setTribunalId(String tribunalId) { this.tribunalId = tribunalId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
}