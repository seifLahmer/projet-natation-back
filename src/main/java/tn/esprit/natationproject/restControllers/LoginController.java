package tn.esprit.natationproject.restControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.natationproject.Entite.Utilisateur;
import tn.esprit.natationproject.repositories.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.natationproject.services.EmailService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");

            Optional<Utilisateur> userOpt = utilisateurRepository.findByEmail(email);

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("error", "Email ou mot de passe incorrect"));
            }

            Utilisateur user = userOpt.get();

            if (!passwordEncoder.matches(password, user.getPassword())) {
                return ResponseEntity.status(401).body(Map.of("error", "Email ou mot de passe incorrect"));
            }

            if (!user.isActive()) {
                return ResponseEntity.status(403).body(Map.of("error", "Compte non activé"));
            }

            // Créer la réponse avec la structure attendue par le frontend
            Map<String, Object> response = new HashMap<>();
            response.put("user", Map.of(
                    "id", user.getId(),
                    "email", user.getEmail(),
                    "role", user.getRole(),
                    "nom", user.getNom(),
                    "prenom", user.getPrenom()
            ));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Erreur serveur: " + e.getMessage()));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            Optional<Utilisateur> userOpt = utilisateurRepository.findByEmail(email);

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body(
                        Map.of("error", "Aucun compte associé à cet email")
                );
            }

            String resetLink = "http://localhost:4200/reset-password?email=" + email;
            emailService.sendResetPasswordEmail(email, resetLink);

            return ResponseEntity.ok(Map.of(
                    "message", "Un email de réinitialisation a été envoyé"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Erreur serveur: " + e.getMessage())
            );
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String newPassword = request.get("newPassword");

            Optional<Utilisateur> userOpt = utilisateurRepository.findByEmail(email);

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body(
                        Map.of("error", "Utilisateur non trouvé")
                );
            }

            Utilisateur user = userOpt.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            utilisateurRepository.save(user);

            return ResponseEntity.ok(Map.of(
                    "message", "Mot de passe mis à jour avec succès"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Erreur serveur: " + e.getMessage())
            );
        }
    }

    @GetMapping("/user-details")
    public ResponseEntity<?> getUserDetails(@RequestParam String email) {
        try {
            Optional<Utilisateur> userOpt = utilisateurRepository.findByEmail(email);
            if (userOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Utilisateur user = userOpt.get();

            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("nom", user.getNom());
            response.put("prenom", user.getPrenom());
            response.put("email", user.getEmail());
            response.put("telephone", user.getTelephone());
            response.put("nomClub", user.getNomClub());
            response.put("adresseClub", user.getAdresseClub()); // Ajout ici
            response.put("dateCreation", user.getDateCreation());
            response.put("role", user.getRole());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Erreur serveur: " + e.getMessage()));
        }
    }


    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody Utilisateur updatedUser) {
        try {
            Optional<Utilisateur> userOpt = utilisateurRepository.findById(updatedUser.getId());

            if (userOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Utilisateur user = userOpt.get();

            // Mettre à jour les champs modifiables
            user.setNom(updatedUser.getNom());
            user.setPrenom(updatedUser.getPrenom());
            user.setEmail(updatedUser.getEmail());
            user.setTelephone(updatedUser.getTelephone());

            // Ne pas mettre à jour nomClub s'il est null dans la requête
            if (updatedUser.getNomClub() != null) {
                user.setNomClub(updatedUser.getNomClub());
            }

            // Ajouter la mise à jour de l'adresse du club
            if (updatedUser.getAdresseClub() != null) {
                user.setAdresseClub(updatedUser.getAdresseClub());
            }

            utilisateurRepository.save(user);

            return ResponseEntity.ok(Map.of("message", "Profil mis à jour avec succès"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Erreur serveur: " + e.getMessage()));
        }
    }}