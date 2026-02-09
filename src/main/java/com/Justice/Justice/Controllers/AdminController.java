package com.Justice.Justice.Controllers;
import com.Justice.Justice.Models.Admin;
import com.Justice.Justice.Models.Avocat;
import com.Justice.Justice.Models.Client;
import com.Justice.Justice.Models.Juge;
import com.Justice.Justice.Models.Affaire;
import com.Justice.Justice.Models.Seance;
import com.Justice.Justice.Repositories.AdminRepository;
import com.Justice.Justice.Repositories.AvocatRepository;
import com.Justice.Justice.Repositories.ClientRepository;
import com.Justice.Justice.Repositories.JugeRepository;
import com.Justice.Justice.Repositories.AffaireRepository;
import com.Justice.Justice.Repositories.SeanceRepository;
import com.Justice.Justice.Config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;
      @Autowired
    private AvocatRepository avocatRepository;
      @Autowired
    private JugeRepository jugeRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AffaireRepository affaireRepository;
    @Autowired
    private SeanceRepository seanceRepository;



    @Autowired 
    private JwtUtil jwtUtil; 

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        String encryptedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encryptedPassword);
        return adminRepository.save(admin);
    }
@PostMapping("/avocats/creer")
public ResponseEntity<?> createAvocat(@RequestBody Avocat avocat, 
                                     @RequestHeader("Authorization") String token) {
    
    // Vérifier le token
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Token manquant");
    }
    
    String jwtToken = token.substring(7);
    
    if (!jwtUtil.validateToken(jwtToken)) {
        return ResponseEntity.status(401).body("Token invalide");
    }
    
    // Vérifier que c'est un ADMIN
    String role = jwtUtil.extractRole(jwtToken);
    if (!"ADMIN".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Admin seulement");
    }
    
    String encryptedPassword = passwordEncoder.encode(avocat.getPassword());
    avocat.setPassword(encryptedPassword);
    Avocat savedAvocat = avocatRepository.save(avocat);
    
    return ResponseEntity.ok(savedAvocat);
}
@GetMapping("/avocats/lister")
public ResponseEntity<?> getAllAvocats(@RequestHeader("Authorization") String token) {
    
    // Vérifier si le token est présent
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Token manquant ou invalide");
    }
    
    // Extraire le token
    String jwtToken = token.substring(7);
    
    if (!jwtUtil.validateToken(jwtToken)) {
        return ResponseEntity.status(401).body("Token expiré ou invalide");
    }
    
    String role = jwtUtil.extractRole(jwtToken);
    if (!"ADMIN".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Admin seulement");
    }
    
    // Récupérer et retourner la liste des avocats
    List<Avocat> avocats = avocatRepository.findAll();
    return ResponseEntity.ok(avocats);
}
@PostMapping("/juges/creer")
public ResponseEntity<?> createJuge(@RequestBody Juge juge, 
                                     @RequestHeader("Authorization") String token) {
    
    // Vérifier le token
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Token manquant");
    }
    
    String jwtToken = token.substring(7);
    
    if (!jwtUtil.validateToken(jwtToken)) {
        return ResponseEntity.status(401).body("Token invalide");
    }
    
    // Vérifier que c'est un ADMIN
    String role = jwtUtil.extractRole(jwtToken);
    if (!"ADMIN".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Admin seulement");
    }
    
    // Créer le juge
    String encryptedPassword = passwordEncoder.encode(juge.getPassword());
    juge.setPassword(encryptedPassword);
    Juge savedJuge = jugeRepository.save(juge);
    
    return ResponseEntity.ok(savedJuge);
}
@GetMapping("/juges/lister")
public ResponseEntity<?> getAllJuges(@RequestHeader("Authorization") String token) {
    
    // Vérifier si le token est présent
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Token manquant ou invalide");
    }
    
    String jwtToken = token.substring(7);
    
    if (!jwtUtil.validateToken(jwtToken)) {
        return ResponseEntity.status(401).body("Token expiré ou invalide");
    }
    
    String role = jwtUtil.extractRole(jwtToken);
    if (!"ADMIN".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Admin seulement");
    }
    
    List<Juge> juges = jugeRepository.findAll();
    return ResponseEntity.ok(juges);
}

@GetMapping("/affaires")
public ResponseEntity<?> getMesAffaires(@RequestHeader("Authorization") String token) {
    
    // Vérifier le token
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Token manquant");
    }
    
    String jwtToken = token.substring(7);
    
    if (!jwtUtil.validateToken(jwtToken)) {
        return ResponseEntity.status(401).body("Token invalide");
    }
    
    String role = jwtUtil.extractRole(jwtToken);
    if (!"ADMIN".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Admin seulement");
    }

    List<Affaire> affaires = affaireRepository.findAll();
    
    return ResponseEntity.ok(affaires);
}
@GetMapping("/seances")
public ResponseEntity<?> getMesSeances(@RequestHeader("Authorization") String token) {
    
    // Vérifier le token
    if (token == null || !token.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Token manquant");
    }
    
    String jwtToken = token.substring(7);
    
    if (!jwtUtil.validateToken(jwtToken)) {
        return ResponseEntity.status(401).body("Token invalide");
    }
    
    String role = jwtUtil.extractRole(jwtToken);
    if (!"ADMIN".equals(role)) {
        return ResponseEntity.status(403).body("Accès refusé - Admin seulement");
    }

    List<Seance> seances = seanceRepository.findAll();
    
    return ResponseEntity.ok(seances);
}

@PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        Map<String, Object> response = new HashMap<>();
        
        String adminId = loginData.get("adminId");
        String password = loginData.get("password");
        
Optional<Admin> adminOpt = adminRepository.findByAdminId(adminId);        
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            
            if (passwordEncoder.matches(password, admin.getPassword())) {
                if (jwtUtil != null) {
                    String token = jwtUtil.generateToken(admin.getAdminId(), "ADMIN");
                    response.put("success", true);
                    response.put("message", "Connexion admin réussie");
                    response.put("token", token);
                    response.put("admin", admin);
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
            response.put("message", "Admin non trouvé");
        }
        
        return response;
    }
}