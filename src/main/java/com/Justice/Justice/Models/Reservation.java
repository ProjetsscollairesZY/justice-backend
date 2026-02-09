package com.Justice.Justice.Models;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(name = "reservations")
public class Reservation {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationId")
   
    private Long reservationId;

    // Relation avec Salle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salleId", referencedColumnName = "salleId")
        @JsonIgnore
    private Salle salle;

    // Relation avec Seance
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seanceId", referencedColumnName = "seanceId")
     @JsonIgnore
    private Seance seance;

    @Column(name = "dateReservee")
    private LocalDate dateReservee;
    @Column(name = "heureReservee")
    private LocalTime heureReservee;   

    // Constructeurs
    public Reservation() {}

    public Reservation(Salle salle, Seance seance, LocalDate dateReservee, LocalTime heureReservee) {
        this.salle = salle;
        this.seance = seance;
        this.dateReservee = dateReservee;
        this.heureReservee = heureReservee;
    }

    // Getters et Setters
    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

    public Salle getSalle() { return salle; }
    public void setSalle(Salle salle) { this.salle = salle; }

    public Seance getSeance() { return seance; }
    public void setSeance(Seance seance) { this.seance = seance; }

    public LocalDate getDateReservee() { return dateReservee; }
    public void setDateReservee(LocalDate dateReservee) { this.dateReservee = dateReservee; }

    // Méthode utilitaire pour avoir juste l'ID de la salle (facultatif)
    public String getSalleId() {
        return salle != null ? salle.getSalleId() : null;
    }

    // Méthode utilitaire pour avoir juste l'ID de la séance (facultatif)
    public String getSeanceId() {
        return seance != null ? seance.getSeanceId() : null;
    }

    public LocalTime getHeureReservee() { return heureReservee; }
    public void setHeureReservee(LocalTime heureReservee) { this.heureReservee = heureReservee; }
}