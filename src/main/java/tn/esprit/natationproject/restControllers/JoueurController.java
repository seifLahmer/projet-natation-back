package tn.esprit.natationproject.restControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.natationproject.Entite.Utilisateur;
import tn.esprit.natationproject.repositories.UtilisateurRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
            String chefEmail = request.get("chefEmail"); // Nouveau paramètre

            if (email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().body("L'email est obligatoire");
            }

            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return ResponseEntity.badRequest().body("Format d'email invalide");
            }

            if (utilisateurRepository.existsByEmail(email)) {
                return ResponseEntity.badRequest().body("Email déjà utilisé");
            }

            // Récupérer les infos du chef d'équipe
            Optional<Utilisateur> chefOpt = utilisateurRepository.findByEmail(chefEmail);
            if (chefOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Chef d'équipe non trouvé");
            }
            Utilisateur chef = chefOpt.get();

            String password = generateRandomPassword();

            Utilisateur joueur = new Utilisateur();
            joueur.setEmail(email);
            joueur.setPassword(passwordEncoder.encode(password));
            joueur.setRole("JOUEUR");
            joueur.setActive(true);
            joueur.setDateCreation(LocalDateTime.now());
            joueur.setNom("Joueur");
            joueur.setPrenom("Nouveau");
            joueur.setTelephone("");
            joueur.setNomClub(chef.getNomClub()); // Utilise le nom du club du chef
            joueur.setAdresseClub(chef.getAdresseClub()); // Utilise l'adresse du club du chef

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
            String chefEmail = request.get("chefEmail"); // Nouveau paramètre

            if (email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().body("L'email est obligatoire");
            }

            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return ResponseEntity.badRequest().body("Format d'email invalide");
            }

            if (utilisateurRepository.existsByEmail(email)) {
                return ResponseEntity.badRequest().body("Email déjà utilisé");
            }

            // Récupérer les infos du chef d'équipe
            Optional<Utilisateur> chefOpt = utilisateurRepository.findByEmail(chefEmail);
            if (chefOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Chef d'équipe non trouvé");
            }
            Utilisateur chef = chefOpt.get();

            String password = generateRandomPassword();

            Utilisateur joueur = new Utilisateur();
            joueur.setEmail(email);
            joueur.setPassword(passwordEncoder.encode(password));
            joueur.setRole("JOUEUR");
            joueur.setActive(true);
            joueur.setDateCreation(LocalDateTime.now());
            joueur.setNom(nom != null ? nom : "Joueur");
            joueur.setPrenom(prenom != null ? prenom : "Nouveau");
            joueur.setTelephone(telephone != null ? telephone : "");
            joueur.setNomClub(chef.getNomClub()); // Utilise le nom du club du chef
            joueur.setAdresseClub(chef.getAdresseClub()); // Utilise l'adresse du club du chef

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
    public ResponseEntity<List<Utilisateur>> getJoueursSansClub() {
        return ResponseEntity.ok(utilisateurRepository.findByRoleAndNomClubIsNull("JOUEUR"));
    }

    @GetMapping("/par-club")
    public ResponseEntity<List<Utilisateur>> getJoueursParClub(@RequestParam String nomClub) {
        return ResponseEntity.ok(utilisateurRepository.findByRoleAndNomClub("JOUEUR", nomClub));
    }

    @PutMapping("/modifier")
    public ResponseEntity<?> modifierJoueur(@RequestBody Map<String, Object> request) {
        try {
            Long id = Long.parseLong(request.get("id").toString());
            String nom = (String) request.get("nom");
            String prenom = (String) request.get("prenom");
            String email = (String) request.get("email");
            String telephone = (String) request.get("telephone");

            Optional<Utilisateur> joueurOpt = utilisateurRepository.findById(id);
            if (joueurOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Utilisateur joueur = joueurOpt.get();
            joueur.setNom(nom);
            joueur.setPrenom(prenom);
            joueur.setEmail(email);
            joueur.setTelephone(telephone);

            utilisateurRepository.save(joueur);

            return ResponseEntity.ok(Map.of("message", "Joueur modifié avec succès"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur serveur: " + e.getMessage());
        }
    }

    @PostMapping("/supprimer")
    public ResponseEntity<?> supprimerJoueur(@RequestBody Map<String, Object> request) {
        try {
            Long id = Long.parseLong(request.get("id").toString());
            String email = (String) request.get("email");

            Optional<Utilisateur> joueurOpt = utilisateurRepository.findById(id);
            if (joueurOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Envoyer l'email avant de supprimer
            sendDeletionEmail(email);

            utilisateurRepository.deleteById(id);

            return ResponseEntity.ok(Map.of("message", "Joueur supprimé avec succès"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur serveur: " + e.getMessage());
        }
    }

    private void sendDeletionEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Suppression de l'équipe");
        message.setText("Bonjour,\n\n"
                + "Nous vous informons que vous avez été supprimé de l'équipe par votre chef d'équipe.\n\n"
                + "Si vous pensez qu'il s'agit d'une erreur, veuillez contacter votre club.\n\n"
                + "Cordialement,\n"
                + "L'équipe de la Fédération");
        mailSender.send(message);
    }
}