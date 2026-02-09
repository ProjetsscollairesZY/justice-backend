package com.Justice.Justice.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "avocats")
public class Avocat {
    @Id
    @Column(name = "avocat_id")
    private String avocatId;  

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

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "specialite")
    private String specialite;

   public Avocat() {
    }

    public Avocat(String avocatId, String nom, String prenom, String email, 
                  String password, String telephone, String adresse, String specialite) {
        this.avocatId = avocatId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.adresse = adresse;
        this.specialite = specialite;
    }


    public String getAvocatId() { return avocatId; }
    public void setAvocatId(String avocatId) { this.avocatId = avocatId; }

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

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

}
