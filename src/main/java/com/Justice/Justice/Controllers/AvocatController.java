package com.Justice.Justice.Controllers;

import com.Justice.Justice.Models.Avocat;
import com.Justice.Justice.Models.Client;
import com.Justice.Justice.Models.Reservation;
import com.Justice.Justice.Models.Seance;
import com.Justice.Justice.Models.Affaire;
import com.Justice.Justice.Models.ArchiveAffaire;
import com.Justice.Justice.Repositories.AvocatRepository;
import com.Justice.Justice.Repositories.ClientRepository;
import com.Justice.Justice.Repositories.AffaireRepository;
import com.Justice.Justice.Config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.Justice.Justice.Repositories.ArchiveAffaireRepository;
import com.Justice.Justice.Repositories.SeanceRepository;
import com.Justice.Justice.Repositories.ReservationRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/avocats")
public class AvocatController {
@Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AvocatRepository avocatRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AffaireRepository affaireRepository;

    @Autowired 
    private JwtUtil jwtUtil;
    @Autowired
    private ArchiveAffaireRepository archiveAffaireRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/mes-seances")
public ResponseEntity<?> getMesSeances(@RequestHeader("Authorization") String token) {
    
    // Vérifier le token
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Token manquant");
    }
    
    String jwtToken = token.substring(7);
    
    if (!jwtUtil.validateToken(jwtToken)) {
        return ResponseEntity.status(401).body("Token invalide");
    }
    
    // Vérifier que c'est un AVOCAT
    String role = jwtUtil.extractRole(jwtToken);
    if (!"AVOCAT".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Avocat seulement");
    }
    
    // Récupérer l'ID de l'avocat connecté
    String avocatId = jwtUtil.extractUsername(jwtToken);
    
    List<Seance> mesSeances = seanceRepository.findByAvocatId(avocatId);
    
    List<Map<String, Object>> response = new ArrayList<>();
    
    for (Seance seance : mesSeances) {
        List<Reservation> reservations = reservationRepository.findBySeanceSeanceId(seance.getSeanceId());
        
        Map<String, Object> seanceWithReservations = new HashMap<>();
        seanceWithReservations.put("seance", seance);
        seanceWithReservations.put("reservations", reservations);
        seanceWithReservations.put("nombreReservations", reservations.size());
        
        response.add(seanceWithReservations);
    }
    
    return ResponseEntity.ok(response);
}
@PostMapping("/clients/creer")
public ResponseEntity<?> creerClient(
        @RequestBody Client client,
        @RequestHeader("Authorization") String token) {

    // Vérifier le token
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Token manquant");
    }

    String jwtToken = token.substring(7);

    if (!jwtUtil.validateToken(jwtToken)) {
        return ResponseEntity.status(401).body("Token invalide");
    }

    // Vérifier que c'est un AVOCAT
    String role = jwtUtil.extractRole(jwtToken);
    if (!"AVOCAT".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Avocat seulement");
    }

    // Récupérer l'ID de l'avocat depuis le token
    String avocatId = jwtUtil.extractUsername(jwtToken);
    
    // 🔥 CORRECTION : Associer le client à cet avocat
    client.setAvocatId(avocatId);

    // Debug pour vérifier
    System.out.println("🟢 Avocat ID: " + avocatId);
    System.out.println("🟢 Client avocatId: " + client.getAvocatId());

    // Sauvegarder
    Client savedClient = clientRepository.save(client);
    return ResponseEntity.ok(savedClient);
}
@PostMapping("/affaires/creer")
public ResponseEntity<?> creerAffaire(@RequestBody Affaire affaire, 
                                     @RequestHeader("Authorization") String token) {
    
    // Vérifier le token
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Token manquant");
    }
    
    String jwtToken = token.substring(7);
    
    if (!jwtUtil.validateToken(jwtToken)) {
        return ResponseEntity.status(401).body("Token invalide");
    }
    
    // Vérifier que c'est un AVOCAT
    String role = jwtUtil.extractRole(jwtToken);
    if (!"AVOCAT".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Avocat seulement");
    }
    
    String avocatId = jwtUtil.extractUsername(jwtToken);
    affaire.setAvocatId(avocatId);
    
    if (affaire.getClientId() != null && !affaire.getClientId().isEmpty()) {
        Optional<Client> clientOpt = clientRepository.findByClientId(affaire.getClientId());
        if (clientOpt.isEmpty()) {
            return ResponseEntity.status(404)
                .body("Client non trouvé avec l'ID: " + affaire.getClientId());
        }
        
        Client client = clientOpt.get();
        if (!avocatId.equals(client.getAvocatId())) {
            return ResponseEntity.status(403)
                .body("Ce client n'appartient pas à votre cabinet");
        }
    }
    
    // Créer l'affaire
    Affaire savedAffaire = affaireRepository.save(affaire);
    return ResponseEntity.ok(savedAffaire);
}
@GetMapping("/mes-affaires")
public ResponseEntity<?> getMesAffaires(@RequestHeader("Authorization") String token) {
    
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Token manquant");
    }
    
    String jwtToken = token.substring(7);
    
    if (!jwtUtil.validateToken(jwtToken)) {
        return ResponseEntity.status(401).body("Token invalide");
    }
    
    String role = jwtUtil.extractRole(jwtToken);
    if (!"AVOCAT".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Avocat seulement");
    }
    
    String avocatId = jwtUtil.extractUsername(jwtToken);
    
    List<Affaire> mesAffaires = affaireRepository.findByAvocatId(avocatId);
    return ResponseEntity.ok(mesAffaires);
}

@GetMapping("/mes-clients")
public ResponseEntity<?> getMesClients(@RequestHeader("Authorization") String token) {
    
    // Vérifier le token
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Token manquant");
    }
    
    String jwtToken = token.substring(7);
    
    if (!jwtUtil.validateToken(jwtToken)) {
        return ResponseEntity.status(401).body("Token invalide");
    }
    
    // Vérifier que c'est un AVOCAT
    String role = jwtUtil.extractRole(jwtToken);
    if (!"AVOCAT".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Avocat seulement");
    }
    
    String avocatId = jwtUtil.extractUsername(jwtToken);
    
    List<Client> mesClients = clientRepository.findByAvocatId(avocatId);

    return ResponseEntity.ok(mesClients);
}@PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        Map<String, Object> response = new HashMap<>();
        
        String avocatId = loginData.get("avocatId");
        String password = loginData.get("password");
        
Optional<Avocat> avocatOpt = avocatRepository.findByAvocatId(avocatId);
        
        if (avocatOpt.isPresent()) {
            Avocat avocat = avocatOpt.get();
            
            if (passwordEncoder.matches(password, avocat.getPassword())) {
                if (jwtUtil != null) {
String token = jwtUtil.generateToken(avocat.getAvocatId(), "AVOCAT");
                    response.put("success", true);
                    response.put("message", "Connexion avocat réussie");
                    response.put("token", token);
                    response.put("avocat", avocat);
                } else {
                    response.put("success", false);
                    response.put("message", "Erreur serveur - JWT non initialisé");
                }
            } else {
                response.put("success", false);
                response.put("message", "Mot de passe incorrect");
            }
        } else {
            response.put("success", false);
            response.put("message", "avocat non trouvé");
        }
        
        return response;
    }


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
    
    // Vérifier que c'est un AVOCAT
    String role = jwtUtil.extractRole(jwtToken);
    if (!"AVOCAT".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Avocat seulement");
    }
    
    String avocatId = jwtUtil.extractUsername(jwtToken);
    
Optional<Avocat> avocatOpt = avocatRepository.findByAvocatId(avocatId);
    if (avocatOpt.isEmpty()) {
        return ResponseEntity.status(404).body("Avocat non trouvé");
    }
    
    return ResponseEntity.ok(avocatOpt.get());
}
@PutMapping("/affaires/{affaireId}/statut")
public ResponseEntity<?> changerStatutAffaire(
        @PathVariable String affaireId,
        @RequestBody Map<String, String> statutData,
        @RequestHeader("Authorization") String token) {
    
    // Vérifier le token
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Token manquant");
    }
    
    String jwtToken = token.substring(7);
    
    if (!jwtUtil.validateToken(jwtToken)) {
        return ResponseEntity.status(401).body("Token invalide");
    }
    
    // Vérifier que c'est un AVOCAT
    String role = jwtUtil.extractRole(jwtToken);
    if (!"AVOCAT".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Avocat seulement");
    }
    
    // Récupérer l'ID de l'avocat
    String avocatId = jwtUtil.extractUsername(jwtToken);
    
    Optional<Affaire> affaireOpt = affaireRepository.findByAffaireId(affaireId);
    if (affaireOpt.isEmpty()) {
        return ResponseEntity.status(404).body("Affaire non trouvée");
    }
    
    Affaire affaire = affaireOpt.get();
    
    if (!avocatId.equals(affaire.getAvocatId())) {
        return ResponseEntity.status(403)
            .body("Cette affaire ne vous appartient pas");
    }
    
    String nouveauStatut = statutData.get("statut");
    
    // Changer le statut
    affaire.setStatut(nouveauStatut);
    
    // Sauvegarder
    Affaire updatedAffaire = affaireRepository.save(affaire);
    
    return ResponseEntity.ok(updatedAffaire);
}


@GetMapping("/mes-archives")
public ResponseEntity<?> getMesArchives(@RequestHeader("Authorization") String token) {
    
  
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Token manquant");
    }
    
    String jwtToken = token.substring(7);
    
    if (!jwtUtil.validateToken(jwtToken)) {
        return ResponseEntity.status(401).body("Token invalide");
    }
    

    String role = jwtUtil.extractRole(jwtToken);
    if (!"AVOCAT".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Avocat seulement");
    }
    

    String avocatId = jwtUtil.extractUsername(jwtToken);
    

    List<ArchiveAffaire> mesArchives = archiveAffaireRepository.findByAvocatId(avocatId);
    
    return ResponseEntity.ok(mesArchives);
}
}