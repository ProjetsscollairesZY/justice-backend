package com.Justice.Justice.Repositories;

import com.Justice.Justice.Models.Reservation;
import com.Justice.Justice.Models.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Vérifie si une salle est déjà réservée à une date/heure donnée
    boolean existsBySalle_SalleIdAndDateReserveeAndHeureReservee(String salleId, LocalDate dateReservee, LocalTime heureReservee);

    // Vérifie si une séance a déjà une réservation
    boolean existsBySeance(Seance seance);
        long countByDateReserveeAndHeureReservee(LocalDate date, LocalTime heure);

    // Trouve toutes les réservations pour une salle à une date donnée
    List<Reservation> findBySalle_SalleIdAndDateReservee(String salleId, LocalDate dateReservee);

    Optional<Reservation> findBySeance_SeanceId(String seanceId);

    List<Reservation> findBySeanceSeanceId(String id);

    List<Reservation> findByDateReserveeAndHeureReservee(LocalDate dateRecherche, LocalTime heureRecherche);

    List<Reservation> findByDateReservee(LocalDate dateRecherche);
}