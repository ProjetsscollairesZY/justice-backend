package com.Justice.Justice.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "secretaires")
public class Secretaire {
    @Id
    @Column(name = "secretaireId")
    private String secretaireId;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "tribunal_id")
    private String tribunalId;

    @Column(name = "adresse")
    private String adresse;

  
    public Secretaire() {}

    public Secretaire(String secretaireId, String nom, String prenom, String email, String password, String telephone, String tribunalId,String adresse) {
        this.secretaireId = secretaireId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.tribunalId = tribunalId;
        this.adresse = adresse;
    }

    
    public String getSecretaireId() { return secretaireId; }
    public void setSecretaireId(String secretaireId) { this.secretaireId = secretaireId; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getTribunalId() { return tribunalId; }
    public void setTribunalId(String tribunalId) { this.tribunalId = tribunalId; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

}