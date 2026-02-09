package com.Justice.Justice.Controllers;

import com.Justice.Justice.Models.Juge;
import com.Justice.Justice.Models.Reservation;
import com.Justice.Justice.Models.Seance;
import com.Justice.Justice.Repositories.JugeRepository;
import com.Justice.Justice.Repositories.SeanceRepository;
import com.Justice.Justice.Config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Justice.Justice.Repositories.ReservationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/juges")
public class JugeController {

    @Autowired
    private JugeRepository jugeRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired 
    private JwtUtil jwtUtil;

    // === CONNEXION JUGE ===
    @SuppressWarnings("unused")
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        Map<String, Object> response = new HashMap<>();
        
        String jugeId = loginData.get("jugeId");
        String password = loginData.get("password");
        
Optional<Juge> jugeOpt = jugeRepository.findByJugeId(jugeId);
        
        if (jugeOpt.isPresent()) {
            Juge juge = jugeOpt.get();
            
   
            if (juge.getPassword() != null && !juge.getPassword().isEmpty()) {
    
            }
            
            // Générer le token
String token = jwtUtil.generateToken(juge.getJugeId(), "JUGE");
            
            response.put("success", true);
            response.put("message", "Connexion juge réussie");
            response.put("token", token);
            response.put("juge", juge);
            
        } else {
            response.put("success", false);
            response.put("message", "Juge non trouvé");
        }
        
        return response;
    }

    // === MES SÉANCES (celles où le juge est concerné) ===
   @GetMapping("/mes-seances")
public ResponseEntity<?> getMesSeances(@RequestHeader("Authorization") String token) {
    // Validation token et rôle
    if (token == null || !token.startsWith("Bearer ")) 
        return ResponseEntity.status(401).body("Token manquant");
    
    String jwtToken = token.substring(7);
    if (!jwtUtil.validateToken(jwtToken)) 
        return ResponseEntity.status(401).body("Token invalide");
    
    if (!"JUGE".equals(jwtUtil.extractRole(jwtToken))) 
        return ResponseEntity.status(403).body("Accès refusé - Juge seulement");
    
    String jugeId = jwtUtil.extractUsername(jwtToken);
    
    // Récupérer les séances du juge
    List<Seance> seances = seanceRepository.findByJugeId(jugeId);
    
    // Pour chaque séance, récupérer la réservation associée
    List<Map<String, Object>> result = new ArrayList<>();
    
    for (Seance seance : seances) {
        Map<String, Object> seanceInfo = new HashMap<>();
        seanceInfo.put("seance", seance);
        
        // Chercher la réservation pour cette séance
        Optional<Reservation> reservationOpt = reservationRepository.findBySeance_SeanceId(seance.getSeanceId());
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            Map<String, Object> reservationInfo = new HashMap<>();
            reservationInfo.put("salle", reservation.getSalle());
            reservationInfo.put("dateReservee", reservation.getDateReservee());
            reservationInfo.put("heureReservee", reservation.getHeureReservee());
            seanceInfo.put("reservation", reservationInfo);
        }
        
        result.add(seanceInfo);
    }
    
    return ResponseEntity.ok(result);
}
    // === VOIR MES INFORMATIONS ===
    @GetMapping("/mon-profil")
    public ResponseEntity<?> getMonProfil(@RequestHeader("Authorization") String token) {
        
        // Vérifier le token
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token manquant");
        }
        
        String jwtToken = token.substring(7);
        
        if (!jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(401).body("Token invalide");
        }
        
        // Vérifier que c'est un JUGE
        String role = jwtUtil.extractRole(jwtToken);
        if (!"JUGE".equals(role)) {
            return ResponseEntity.status(403).body("Accès refusé - Juge seulement");
        }
        
        // Récupérer l'ID du juge connecté
        String jugeId = jwtUtil.extractUsername(jwtToken);
        
        // Récupérer le juge
Optional<Juge> jugeOpt = jugeRepository.findByJugeId(jugeId);
        if (jugeOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Juge non trouvé");
        }
        
        return ResponseEntity.ok(jugeOpt.get());
    }
}