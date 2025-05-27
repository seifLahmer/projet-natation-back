package tn.esprit.natationproject.restControllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.natationproject.Entite.*;
import tn.esprit.natationproject.repositories.CompetitionRepository;
import tn.esprit.natationproject.repositories.InscriptionRepository;
import tn.esprit.natationproject.repositories.UtilisateurRepository;
import tn.esprit.natationproject.services.EmailService;
import tn.esprit.natationproject.services.IInscriptionService;
import tn.esprit.natationproject.Entite.Utilisateurs;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/inscriptions")
@Tag(name = "gestion des inscriptions")
@AllArgsConstructor
public class InscriptionRestController {
    private IInscriptionService inscriptionService;
    private EmailService emailService;
    private UtilisateurRepository utilisateurRepository;
    private CompetitionRepository competitionRepository;
    private InscriptionRepository inscriptionRepository;
    @GetMapping
    public List<Inscription> getInscriptions() { return inscriptionService.getInscriptions(); }

    @GetMapping("/{id}")
    public Inscription getInscription(@PathVariable int id) { return inscriptionService.getInscription(id); }

    @PostMapping
    public Inscription addInscription(@RequestBody Inscription inscription) {return inscriptionService.addInscription(inscription); }

    @PutMapping
    public Inscription updateInscription(@RequestBody Inscription inscription) {return inscriptionService.updateInscription(inscription); }

    @DeleteMapping("/{id}")
    public void deleteInscription(@PathVariable int id) { inscriptionService.deleteInscription(id); }


    @PostMapping("/inscrire/{idCompetition}")
    public ResponseEntity<String> inscrire(@PathVariable int idCompetition,
                                           @RequestBody Map<String, Object> requestBody) {
        if (!requestBody.containsKey("idUtilisateur")) {
            return ResponseEntity.badRequest().body("ID utilisateur manquant dans le corps de la requête.");
        }

        Long idUtilisateur;
        try {
            idUtilisateur = Long.parseLong(requestBody.get("idUtilisateur").toString());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("ID utilisateur invalide.");
        }


        inscriptionService.inscrireUtilisateurACompetition(idCompetition, idUtilisateur);

        // Récupération de l'utilisateur depuis la base
        Optional<Utilisateurs> utilisateurOpt = utilisateurRepository.findById((long) idUtilisateur);
        Optional<Competition> competitionOpt = competitionRepository.findById(idCompetition);

        if (utilisateurOpt.isPresent() && competitionOpt.isPresent()) {
            Utilisateurs utilisateurs = utilisateurOpt.get();
            Competition competition = competitionOpt.get();

            // Template HTML pour l'e-mail
            String html = "<html>" +
                    "<body style='font-family: Arial, sans-serif; color: #333;'>"
                    + "<div style='border:1px solid #dce3ec; padding:20px; border-radius:8px;'>"
                    + "<h2 style='color:#1E88E5;'>Bonjour " + utilisateurs.getNom() + ",</h2>"
                    + "<p>🎉 Vous avez été <strong>inscrit(e)</strong> à la compétition de <strong>" + competition.getTypeC().name() + "</strong>.</p>"
                    + "<p style='font-size:16px;'>📌 <strong>Statut :</strong> <span style='color: orange;'>En attente de validation</span></p>"
                    + "<p>Nous vous remercions pour votre participation et vous souhaitons bonne chance ! 🏊‍♂️</p>"
                    + "<br><p style='font-size: 12px; color: #999;'>Équipe Natation Esprit</p>"
                    + "</div></body></html>";

            try {
                emailService.envoyerEmailHtml(utilisateurs.getEmail(), "Votre inscription est en attente", html);
            } catch (MessagingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'e-mail HTML.");
            }

            return ResponseEntity.ok("Inscription effectuée et e-mail HTML envoyé.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Utilisateur ou compétition introuvable.");
        }
    }


    @GetMapping("/inscriptionsUser")
    public ResponseEntity<List<Inscription>> getInscriptionsParUtilisateur(
            @RequestParam Long userId,
            @RequestParam String role) {

        List<Inscription> inscriptions;

        if ("ADMIN".equalsIgnoreCase(role)) {
            inscriptions = inscriptionService.getInscriptions();
        } else {
            inscriptions = inscriptionService.getInscriptionsByUserId(userId);
        }

        return ResponseEntity.ok(inscriptions);
    }

    @GetMapping("/{id}/competition")
    public ResponseEntity<Competition> getCompetitionForInscription(@PathVariable int id) {
        Competition competition = inscriptionService.getCompetitionByInscriptionId(id);
        return ResponseEntity.ok(competition);
    }

    @PutMapping("/inscription/{id}/statut")
    public ResponseEntity<String> changerStatut(@PathVariable int id, @RequestParam String statut) {
        Optional<Inscription> inscriptionOpt = inscriptionRepository.findById(id);

        if (inscriptionOpt.isPresent()) {
            Inscription inscription = inscriptionOpt.get();

            StatutInscription nouveauStatut;
            try {
                nouveauStatut = StatutInscription.valueOf(statut.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Statut invalide. Valeurs possibles : EN_ATTENTE, CONFIRMEE, ANNULEE, REJETEE");
            }

            inscription.setStatut(nouveauStatut);
            inscriptionRepository.save(inscription);

            Utilisateurs utilisateurs = inscription.getUtilisateurs();
            Competition competition = inscription.getCompetition();

            // Choix de la couleur en fonction du statut
            String couleur = switch (nouveauStatut) {
                case CONFIRMEE -> "green";
                case ANNULEE -> "orange";
                case REJETEE -> "red";
                case EN_ATTENTE -> "gray";
            };

            // Construction de l'e-mail HTML
            String html = "<html><body style='font-family: Arial; color: #333;'>" +
                    "<div style='padding:20px; border:1px solid #dce3ec; border-radius:10px;'>"
                    + "<h2>Bonjour " + utilisateurs.getNom() + ",</h2>"
                    + "<p>Le statut de votre inscription à la compétition <strong>" + competition.getTypeC().name() + "</strong> a été mis à jour.</p>"
                    + "<p style='font-size:16px;'>📌 Nouveau statut : <strong style='color:" + couleur + ";'>" + nouveauStatut.name().replace("_", " ") + "</strong></p>"
                    + "<p>Merci pour votre participation et à très bientôt en piscine 🏊‍♂️ !</p>"
                    + "<br><p style='font-size: 12px; color: #888;'>Équipe Natation Esprit</p>"
                    + "</div></body></html>";

            try {
                emailService.envoyerEmailHtml(utilisateurs.getEmail(), "Mise à jour de votre inscription", html);
            } catch (MessagingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'e-mail.");
            }

            return ResponseEntity.ok("Statut mis à jour et e-mail envoyé.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inscription introuvable.");
    }



}
