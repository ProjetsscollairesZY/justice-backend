package com.Justice.Justice.Models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @Column(name = "client_id")
    private String clientId;  // Changé de idClient à clientId

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "type_client")
    private String typeClient;

    @Column(name = "date_de_naissance")
    private LocalDate dateDeNaissance;

    @Column(name = "willaya")
    private String willaya;

    @Column(name = "avocat_id")
    private String avocatId;


    public Client() {}

    public Client(String clientId, String nom, String prenom, String email, String telephone, String adresse, String typeClient,LocalDate dateDeNaissance,String willaya, String avocatId) {
        this.clientId = clientId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.typeClient = typeClient;
        this.dateDeNaissance = dateDeNaissance;
        this.willaya = willaya;
                this.avocatId = avocatId;

    }
    
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getTypeClient() { return typeClient; }
    public void setTypeClient(String typeClient) { this.typeClient = typeClient; }
    public LocalDate getDateDeNaissance() { return dateDeNaissance; }
    public void setDateDeNaissance(LocalDate dateDeNaissance) { this.dateDeNaissance = dateDeNaissance; }
    public String getWillaya() { return willaya; }
    public void setWillaya(String willaya) { this.willaya = willaya; }


    public String getAvocatId() { return avocatId; }
    public void setAvocatId(String avocatId) { this.avocatId = avocatId; }





}