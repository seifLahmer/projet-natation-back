package tn.esprit.natationproject.restControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.natationproject.Entite.Utilisateurs;
import tn.esprit.natationproject.repositories.UtilisateurRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/joueurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class JoueurController {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @PostMapping("/ajouter-par-email")
    public ResponseEntity<?> ajouterJoueurParEmail(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String nomClub = request.get("nomClub");

            if (email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().body("L'email est obligatoire");
            }

            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return ResponseEntity.badRequest().body("Format d'email invalide");
            }

            if (utilisateurRepository.existsByEmail(email)) {
                return ResponseEntity.badRequest().body("Email déjà utilisé");
            }

            String password = generateRandomPassword();

            Utilisateurs joueur = new Utilisateurs();
            joueur.setEmail(email);
            joueur.setPassword(passwordEncoder.encode(password));
            joueur.setRole("JOUEUR");
            joueur.setActive(true);
            joueur.setDateCreation(LocalDateTime.now());
            joueur.setNom("Joueur");
            joueur.setPrenom("Nouveau");
            joueur.setTelephone("");
            joueur.setNomClub(nomClub);

            utilisateurRepository.save(joueur);

            sendCredentialsEmail(email, password);

            return ResponseEntity.ok(Map.of(
                    "message", "Joueur ajouté avec succès",
                    "email", email
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur serveur: " + e.getMessage());
        }
    }

    @PostMapping("/ajouter-par-details")
    public ResponseEntity<?> ajouterJoueurParDetails(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String nom = request.get("nom");
            String prenom = request.get("prenom");
            String telephone = request.get("telephone");
            String nomClub = request.get("nomClub");

            if (email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().body("L'email est obligatoire");
            }

            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return ResponseEntity.badRequest().body("Format d'email invalide");
            }

            if (utilisateurRepository.existsByEmail(email)) {
                return ResponseEntity.badRequest().body("Email déjà utilisé");
            }

            String password = generateRandomPassword();

            Utilisateurs joueur = new Utilisateurs();
            joueur.setEmail(email);
            joueur.setPassword(passwordEncoder.encode(password));
            joueur.setRole("JOUEUR");
            joueur.setActive(true);
            joueur.setDateCreation(LocalDateTime.now());
            joueur.setNom(nom != null ? nom : "Joueur");
            joueur.setPrenom(prenom != null ? prenom : "Nouveau");
            joueur.setTelephone(telephone != null ? telephone : "");
            joueur.setNomClub(nomClub);

            utilisateurRepository.save(joueur);

            sendCredentialsEmail(email, password);

            return ResponseEntity.ok(Map.of(
                    "message", "Joueur ajouté avec succès",
                    "email", email
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur serveur: " + e.getMessage());
        }
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private void sendCredentialsEmail(String email, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Inscription à la Fédération Tunisienne de Natation");
        message.setText("Bonjour,\n\n"
                + "Vous avez été inscrit sur la plateforme de la Fédération Tunisienne de Natation.\n\n"
                + "Voici vos identifiants :\n"
                + "Email: " + email + "\n"
                + "Mot de passe: " + password + "\n\n"
                + "Vous pouvez vous connecter à l'adresse : http://localhost:4200/login\n\n"
                + "Cordialement,\n"
                + "L'équipe de la Fédération");
        mailSender.send(message);
    }

    @GetMapping("/sans-club")
    public ResponseEntity<List<Utilisateurs>> getJoueursSansClub() {
        return ResponseEntity.ok(utilisateurRepository.findByRoleAndNomClubIsNull("JOUEUR"));
    }
}