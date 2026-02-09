package com.Justice.Justice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import com.Justice.Justice.Models.Affaire;
import com.Justice.Justice.Models.Seance;
import com.Justice.Justice.Models.Reservation;
import com.Justice.Justice.Repositories.AffaireRepository;
import com.Justice.Justice.Repositories.SeanceRepository;
import com.Justice.Justice.Repositories.ReservationRepository;

@RestController
@RequestMapping("/api/affaires-seances")
public class AffaireSeanceController {
    
    @Autowired 
    private AffaireRepository affaireRepository;
    
    @Autowired
    private SeanceRepository seanceRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/affaires/{id}")
    public ResponseEntity<?> getAffaireById(@PathVariable("id") String affaireId) {
        Optional<Affaire> affaire = affaireRepository.findById(affaireId);
        
        if (affaire.isPresent()) {
            return ResponseEntity.ok(affaire.get());
        } else {
            return ResponseEntity.status(404).body("Affaire non trouvée");
        }
    }

    @GetMapping("/seances/{id}")
    public ResponseEntity<?> getSeanceById(@PathVariable("id") String seanceId) {
        Optional<Seance> seance = seanceRepository.findById(seanceId);
        
        if (seance.isPresent()) {
            Seance seanceObj = seance.get();
            List<Reservation> reservations = reservationRepository.findBySeanceSeanceId(seanceId);
            
            java.util.Map<String, Object> response = new java.util.HashMap<>();
            response.put("seance", seanceObj);
            response.put("reservations", reservations);
            
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body("Séance non trouvée");
        }
    }
}