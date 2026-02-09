package com.Justice.Justice.Controllers;
import com.Justice.Justice.Models.Affaire;
import com.Justice.Justice.Models.Avocat;
import com.Justice.Justice.Models.Client;
import com.Justice.Justice.Models.Juge;
import com.Justice.Justice.Models.Reservation;
import com.Justice.Justice.Models.Seance;
import com.Justice.Justice.Models.Secretaire;
import com.Justice.Justice.Models.Salle;

import com.Justice.Justice.Repositories.SeanceRepository;
import com.Justice.Justice.Repositories.ClientRepository;
import com.Justice.Justice.Repositories.SecretaireRepository;
import com.Justice.Justice.Repositories.AffaireRepository;
import com.Justice.Justice.Repositories.AvocatRepository;
import com.Justice.Justice.Repositories.SalleRepository;
import com.Justice.Justice.Repositories.JugeRepository;
import com.Justice.Justice.Repositories.ReservationRepository;
import com.Justice.Justice.Config.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/secretaires")
public class SecretaireController {

    @Autowired
    private SecretaireRepository secretaireRepository;
    @Autowired
    private JugeRepository jugeRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AffaireRepository affaireRepository;
    @Autowired
    private AvocatRepository avocatRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // === ENUMS POUR LES STATUTS ===
    public enum StatutAffaire { ACTIVE, EN_COURS, EN_ATTENTE, CLOTUREE, ARCHIVEE }
    public enum StatutSeance { PLANIFIEE, CONFIRMEE, EN_COURS, TERMINEE, ANNULEE, REPORTEE }

    // === VALIDATION TOKEN ET ROLE ===
    private ResponseEntity<String> validateSecretaireToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) return ResponseEntity.status(401).body("Token manquant");
        String jwtToken = token.substring(7);
        if (!jwtUtil.validateToken(jwtToken)) return ResponseEntity.status(401).body("Token invalide");
        if (!"SECRETAIRE".equals(jwtUtil.extractRole(jwtToken))) return ResponseEntity.status(403).body("Accès refusé - Secrétaire seulement");
        return null;
    }

    private String extractSecretaireId(String token) {
        return jwtUtil.extractUsername(token.substring(7));
    }

    // === CREATE SECRETAIRE ===
    @PostMapping
    public Secretaire createSecretaire(@RequestBody Secretaire secretaire) {
        secretaire.setPassword(passwordEncoder.encode(secretaire.getPassword()));
        return secretaireRepository.save(secretaire);
    }

    // === LOGIN ===
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        Map<String, Object> response = new HashMap<>();
        String secretaireId = loginData.get("secretaireId");
        String password = loginData.get("password");

        Optional<Secretaire> secretaireOpt = secretaireRepository.findBySecretaireId(secretaireId);
        if (secretaireOpt.isPresent()) {
            Secretaire secretaire = secretaireOpt.get();
            if (passwordEncoder.matches(password, secretaire.getPassword())) {
                String token = jwtUtil.generateToken(secretaire.getSecretaireId(), "SECRETAIRE");
                response.put("success", true);
                response.put("message", "Connexion réussie");
                response.put("token", token);
                response.put("secretaire", secretaire);
            } else {
                response.put("success", false);
                response.put("message", "Mot de passe incorrect");
            }
        } else {
            response.put("success", false);
            response.put("message", "Secrétaire non trouvé");
        }
        return response;
    }

    // === GET CLIENTS ===
    @GetMapping("/clients")
    public ResponseEntity<?> getMesClients(@RequestHeader("Authorization") String token) {
        ResponseEntity<String> validation = validateSecretaireToken(token);
        if (validation != null) return validation;
        return ResponseEntity.ok(clientRepository.findAll());
    }

    // === GET AVOCATS ===
    @GetMapping("/avocats")
    public ResponseEntity<?> getAllAvocats(@RequestHeader("Authorization") String token) {
        ResponseEntity<String> validation = validateSecretaireToken(token);
        if (validation != null) return validation;
        return ResponseEntity.ok(avocatRepository.findAll());
    }

    // === GET AFFAIRES ===
    @GetMapping("/affaires")
    public ResponseEntity<?> getMesAffaires(@RequestHeader("Authorization") String token) {
        ResponseEntity<String> validation = validateSecretaireToken(token);
        if (validation != null) return validation;
        return ResponseEntity.ok(affaireRepository.findAll());
    }

@PostMapping("/reservations/ajouter")
public ResponseEntity<?> ajouterReservation(
        @RequestBody Map<String, String> data,
        @RequestHeader("Authorization") String token) {

    ResponseEntity<String> validation = validateSecretaireToken(token);
    if (validation != null) return validation;

    String salleId = data.get("salleId");
    String seanceId = data.get("seanceId");
    String dateStr = data.get("dateReservee");
    String heureStr = data.get("heureReservee");

    // Vérifications
    if (salleId == null || seanceId == null || dateStr == null || heureStr == null)
        return ResponseEntity.badRequest().body("Tous les champs sont obligatoires");

    Optional<Salle> salleOpt = salleRepository.findById(salleId);
    if (salleOpt.isEmpty())
        return ResponseEntity.status(404).body("Salle introuvable");

    Optional<Seance> seanceOpt = seanceRepository.findBySeanceId(seanceId);
    if (seanceOpt.isEmpty())
        return ResponseEntity.status(404).body("Séance introuvable");

    LocalDate dateReservee;
    LocalTime heureReservee;

    try {
        dateReservee = LocalDate.parse(dateStr);
        heureReservee = LocalTime.parse(heureStr);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Format date/heure incorrect");
    }

    // Vérifier si la salle est déjà réservée
    if (reservationRepository.existsBySalle_SalleIdAndDateReserveeAndHeureReservee(
            salleId, dateReservee, heureReservee)) {
        return ResponseEntity.badRequest().body("Salle déjà réservée à cette date et heure");
    }

    // Vérifier si la séance a déjà une réservation
    if (reservationRepository.existsBySeance(seanceOpt.get())) {
        return ResponseEntity.badRequest().body("Cette séance est déjà réservée dans une salle");
    }
    long nombreReservations = reservationRepository.countByDateReserveeAndHeureReservee(dateReservee, heureReservee);
if (nombreReservations >= 5) {
    return ResponseEntity.badRequest().body("Maximum 5 salles peuvent être réservées à cette date/heure");
}

    Reservation reservation = new Reservation(
            salleOpt.get(),
            seanceOpt.get(),
            dateReservee,
            heureReservee
    );

    return ResponseEntity.ok(reservationRepository.save(reservation));
}

    // === CREER SEANCE ===
    @PostMapping("/seances/creer")
    public ResponseEntity<?> creerSeance(@RequestBody Seance seance,
                                         @RequestHeader("Authorization") String token) {
        ResponseEntity<String> validation = validateSecretaireToken(token);
        if (validation != null) return validation;

        if (seance.getAffaireId() == null || seance.getAffaireId().trim().isEmpty())
            return ResponseEntity.badRequest().body("L'ID de l'affaire est obligatoire");

        Optional<Affaire> affaireOpt = affaireRepository.findByAffaireId(seance.getAffaireId());
        if (affaireOpt.isEmpty()) return ResponseEntity.status(404).body("Affaire non trouvée");

        Affaire affaire = affaireOpt.get();
        if ("CLOTUREE".equals(affaire.getStatut()) || "ARCHIVEE".equals(affaire.getStatut()))
            return ResponseEntity.badRequest().body("Impossible de créer une séance pour une affaire " + affaire.getStatut());

        seance.setAvocatId(affaire.getAvocatId());

        if (seance.getJugeId() != null && !seance.getJugeId().isEmpty()) {
            if (jugeRepository.findByJugeId(seance.getJugeId()).isEmpty())
                return ResponseEntity.status(404).body("Juge non trouvé avec l'ID: " + seance.getJugeId());
        }

  
        if (seance.getStatut() == null || seance.getStatut().isEmpty())
            seance.setStatut(StatutSeance.PLANIFIEE.name());

        if (seance.getSeanceId() == null || seance.getSeanceId().isEmpty())
            seance.setSeanceId("SEANCE_" + UUID.randomUUID().toString());

        return ResponseEntity.ok(seanceRepository.save(seance));
    }

    // === CHANGER STATUT SEANCE ===
    @PutMapping("/seances/{seanceId}/statut")
    public ResponseEntity<?> changerStatutSeance(@PathVariable String seanceId,
                                                 @RequestBody Map<String, String> statutData,
                                                 @RequestHeader("Authorization") String token) {
        ResponseEntity<String> validation = validateSecretaireToken(token);
        if (validation != null) return validation;

        Optional<Seance> seanceOpt = seanceRepository.findBySeanceId(seanceId);
        if (seanceOpt.isEmpty()) return ResponseEntity.status(404).body("Séance non trouvée");

        Seance seance = seanceOpt.get();
        String nouveauStatut = statutData.get("statut");

        try {
            StatutSeance.valueOf(nouveauStatut);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Statut invalide");
        }

        seance.setStatut(nouveauStatut);
        return ResponseEntity.ok(seanceRepository.save(seance));
    }
@GetMapping("/salles/disponibles")
public ResponseEntity<?> getSallesDisponibles(
        @RequestParam String date,
        @RequestParam(required = false) String heure,
        @RequestHeader("Authorization") String token) {
    
    ResponseEntity<String> validation = validateSecretaireToken(token);
    if (validation != null) return validation;

    try {
        LocalDate dateRecherche = LocalDate.parse(date);
        LocalTime heureRecherche = heure != null ? LocalTime.parse(heure) : null;
        
        // Récupérer toutes les salles
        List<Salle> toutesSalles = salleRepository.findAll();
        
        // Récupérer les réservations pour cette date
        List<Reservation> reservations = heureRecherche != null 
            ? reservationRepository.findByDateReserveeAndHeureReservee(dateRecherche, heureRecherche)
            : reservationRepository.findByDateReservee(dateRecherche);
        
        // IDs des salles déjà réservées
        Set<String> sallesOccupees = reservations.stream()
            .map(res -> res.getSalle().getSalleId())
            .collect(Collectors.toSet());
        
        // Filtrer les salles disponibles
        List<Map<String, Object>> sallesDisponibles = toutesSalles.stream()
            .filter(salle -> !sallesOccupees.contains(salle.getSalleId()))
            .map(salle -> {
                Map<String, Object> salleInfo = new HashMap<>();
                salleInfo.put("salleId", salle.getSalleId());
      
                return salleInfo;
            })
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(sallesDisponibles);
        
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erreur: " + e.getMessage());
    }
}
    // === GET SEANCES ===
    @GetMapping("/seances")
    public ResponseEntity<?> getAllSeances(@RequestHeader("Authorization") String token) {
        ResponseEntity<String> validation = validateSecretaireToken(token);
        if (validation != null) return validation;
        return ResponseEntity.ok(seanceRepository.findAll());
    }

    // === GET JUGES ===
    @GetMapping("/juges")
    public ResponseEntity<?> getAllJuges(@RequestHeader("Authorization") String token) {
        ResponseEntity<String> validation = validateSecretaireToken(token);
        if (validation != null) return validation;
        return ResponseEntity.ok(jugeRepository.findAll());
    }

    // === CREER SALLE ===
    @PostMapping("/salles/creer")
    public ResponseEntity<?> creerSalle(@RequestBody Salle salle,
                                       @RequestHeader("Authorization") String token) {
        ResponseEntity<String> validation = validateSecretaireToken(token);
        if (validation != null) return validation;
        try {
            return ResponseEntity.ok(salleRepository.save(salle));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la création: " + e.getMessage());
        }
    }

    // === GET SALLES ===
    @GetMapping("/salles")
    public ResponseEntity<?> getAllSalles(@RequestHeader("Authorization") String token) {
        ResponseEntity<String> validation = validateSecretaireToken(token);
        if (validation != null) return validation;
        return ResponseEntity.ok(salleRepository.findAll());
    }

    @GetMapping("/mon-profil")
    public ResponseEntity<?> getMonProfil(@RequestHeader("Authorization") String token) {
        ResponseEntity<String> validation = validateSecretaireToken(token);
        if (validation != null) return validation;

        String secretaireId = extractSecretaireId(token);
        Optional<Secretaire> secretaireOpt = secretaireRepository.findBySecretaireId(secretaireId);
        if (secretaireOpt.isEmpty()) return ResponseEntity.status(404).body("Secrétaire non trouvée");

        return ResponseEntity.ok(secretaireOpt.get());
    }


@GetMapping("/reservations")
public ResponseEntity<?> getAllReservations(@RequestHeader("Authorization") String token) {
    ResponseEntity<String> validation = validateSecretaireToken(token);
    if (validation != null) return validation;
    
    try {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok(reservations);
    } catch (Exception e) {
        e.printStackTrace(); // Pour voir l'erreur dans les logs
        return ResponseEntity.status(500).body("Erreur lors de la récupération des réservations: " + e.getMessage());
    }
}

@GetMapping("/reservations/recherche")
public ResponseEntity<?> rechercherReservations(
        @RequestParam String salleId,
        @RequestParam String date,
        @RequestHeader("Authorization") String token) {

    ResponseEntity<String> validation = validateSecretaireToken(token);
    if (validation != null) return validation;

    // Vérifier que la salle existe
    Optional<Salle> salleOpt = salleRepository.findById(salleId);
    if (salleOpt.isEmpty()) {
        return ResponseEntity.status(404).body("Salle non trouvée");
    }

    LocalDate dateRecherche;
    try {
        dateRecherche = LocalDate.parse(date);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Format de date invalide (ex: 2025-02-03)");
    }

    List<Reservation> reservations =
            reservationRepository.findBySalle_SalleIdAndDateReservee(salleId, dateRecherche);

    return ResponseEntity.ok(reservations);
}
@PutMapping("/reservations/{reservationId}")
public ResponseEntity<?> modifierReservation(
        @PathVariable Long reservationId,
        @RequestBody Map<String, String> data,
        @RequestHeader("Authorization") String token) {

    ResponseEntity<String> validation = validateSecretaireToken(token);
    if (validation != null) return validation;

    Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
    if (reservationOpt.isEmpty()) {
        return ResponseEntity.status(404).body("Réservation non trouvée");
    }

    Reservation reservation = reservationOpt.get();

    String salleId = data.get("salleId");
    String dateStr = data.get("dateReservee");
    String heureStr = data.get("heureReservee");

    // Vérifier salle
    Optional<Salle> salleOpt = salleRepository.findById(salleId);
    if (salleOpt.isEmpty())
        return ResponseEntity.badRequest().body("Salle non trouvée");

    LocalDate date;
    LocalTime heure;

    try {
        date = LocalDate.parse(dateStr);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Format de date invalide");
    }

    try {
        heure = LocalTime.parse(heureStr);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Format d'heure invalide");
    }

    // Vérifier que la salle n'est pas déjà occupée
    boolean salleOccupee =
            reservationRepository.existsBySalle_SalleIdAndDateReserveeAndHeureReservee(
                    salleId, date, heure
            );

    if (salleOccupee) {
        return ResponseEntity.badRequest().body("Impossible : salle déjà occupée à cette date/heure");
    }

    reservation.setSalle(salleOpt.get());
    reservation.setDateReservee(date);
    reservation.setHeureReservee(heure);

    return ResponseEntity.ok(reservationRepository.save(reservation));
}

}