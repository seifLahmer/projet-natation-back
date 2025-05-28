package tn.esprit.natationproject.restControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.natationproject.Entite.Utilisateurs;
import tn.esprit.natationproject.Entite.Activity;
import tn.esprit.natationproject.Entite.Utilisateurs;
import tn.esprit.natationproject.repositories.ActivityRepository;
import tn.esprit.natationproject.repositories.UtilisateurRepository;
import tn.esprit.natationproject.services.EmailService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UtilisateurRepository utilisateurRepository;
    private final EmailService emailService;
    private final ActivityRepository activityRepository;

    @GetMapping("/activities")
    public ResponseEntity<Map<String, Object>> getActivities() {
        Map<String, Object> activities = new HashMap<>();
        
        // Récupérer les statistiques
        activities.put("chefsValides", utilisateurRepository.countByActiveTrueAndRole("CHEF_EQUIPE"));
        activities.put("clubsEnregistres", utilisateurRepository.countDistinctClubs());
        
        // Récupérer les chefs en attente de validation
        List<Utilisateurs> chefsEnAttente = utilisateurRepository.findByActiveFalseAndRole("CHEF_EQUIPE");
        activities.put("chefsEnAttente", chefsEnAttente.stream().map(chef -> {
            Map<String, Object> chefMap = new HashMap<>();
            chefMap.put("id", chef.getId());
            chefMap.put("nom", chef.getNom());
            chefMap.put("prenom", chef.getPrenom());
            chefMap.put("email", chef.getEmail());
            chefMap.put("dateCreation", chef.getFormattedDateCreation());
            return chefMap;
        }).collect(Collectors.toList()));

        return ResponseEntity.ok(activities);
    }

    @GetMapping("/chefs-a-valider")
    public ResponseEntity<List<Map<String, Object>>> getChefsEnAttente() {
        List<Utilisateurs> chefs = utilisateurRepository.findByActiveFalseAndRole("CHEF_EQUIPE");

        List<Map<String, Object>> response = chefs.stream().map(chef -> {
            Map<String, Object> chefMap = new HashMap<>();
            chefMap.put("id", chef.getId());
            chefMap.put("nom", chef.getNom());
            chefMap.put("prenom", chef.getPrenom());
            chefMap.put("email", chef.getEmail());
            chefMap.put("telephone", chef.getTelephone());
            chefMap.put("role", chef.getRole());
            chefMap.put("nomClub", chef.getNomClub());
            chefMap.put("adresseClub", chef.getAdresseClub());
            chefMap.put("dateCreation", chef.getFormattedDateCreation());
            chefMap.put("documentPath", chef.getDocumentPath() != null ?
                    "/api/documents/" + chef.getDocumentPath() : null);
            return chefMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/valider-chef/{id}")
    public ResponseEntity<?> validerChef(@PathVariable Long id) {
        Utilisateurs chef = utilisateurRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Chef d'équipe non trouvé avec l'id: " + id));

        chef.setActive(true);
        utilisateurRepository.save(chef);

        // Enregistrement de l'activité
        Activity activity = new Activity();
        activity.setAction("Chef validé");
        activity.setDetails(String.format(" Nom: %s %s, Club: %s",
                chef.getNom(), chef.getPrenom(), chef.getNomClub()));
        activity.setTimestamp(LocalDateTime.now());
        activityRepository.save(activity);

        try {
            emailService.sendValidationEmail(
                    chef.getEmail(),
                    chef.getNom(),
                    chef.getPrenom(),
                    chef.getNomClub()
            );
            return ResponseEntity.ok(Map.of(
                    "message",
                    "Chef validé avec succès et email de confirmation envoyé"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "message",
                    "Chef validé mais échec d'envoi d'email: " + e.getMessage()
            ));
        }
    }

    @DeleteMapping("/rejeter-chef/{id}")
    public ResponseEntity<?> rejeterChef(@PathVariable Long id) {
        Utilisateurs chef = utilisateurRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Chef d'équipe non trouvé avec l'id: " + id));

        // Enregistrement de l'activité avant suppression
        Activity activity = new Activity();
        activity.setAction("Chef rejeté");
        activity.setDetails(String.format("Nom: %s %s, Club: %s",
                chef.getNom(), chef.getPrenom(), chef.getNomClub()));
        activity.setTimestamp(LocalDateTime.now());
        activityRepository.save(activity);

        utilisateurRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Chef rejeté avec succès"));
    }

    @PutMapping("/modifier-chef/{id}")
    public ResponseEntity<?> modifierChef(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        Utilisateurs chef = utilisateurRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Chef d'équipe non trouvé avec l'id: " + id));

        // Sauvegarder les anciennes valeurs pour les logs
        String ancienNom = chef.getNom();
        String ancienPrenom = chef.getPrenom();
        String ancienClub = chef.getNomClub();

        if (updates.containsKey("nom")) {
            chef.setNom(updates.get("nom"));
        }
        if (updates.containsKey("prenom")) {
            chef.setPrenom(updates.get("prenom"));
        }
        if (updates.containsKey("email")) {
            chef.setEmail(updates.get("email"));
        }
        if (updates.containsKey("telephone")) {
            chef.setTelephone(updates.get("telephone"));
        }
        if (updates.containsKey("nomClub")) {
            chef.setNomClub(updates.get("nomClub"));
        }
        if (updates.containsKey("adresseClub")) {
            chef.setAdresseClub(updates.get("adresseClub"));
        }

        utilisateurRepository.save(chef);

        // Enregistrement de l'activité
        Activity activity = new Activity();
        activity.setAction("Chef modifié");
        activity.setDetails(String.format("Ancien: %s %s, Club: %s | Nouveau: %s %s, Club: %s",
                ancienNom, ancienPrenom, ancienClub,
                chef.getNom(), chef.getPrenom(), chef.getNomClub()));
        activity.setTimestamp(LocalDateTime.now());
        activityRepository.save(activity);

        return ResponseEntity.ok(Map.of("message", "Chef modifié avec succès"));
    }
  @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("chefsValides", utilisateurRepository.countByActiveTrueAndRole("CHEF_EQUIPE"));
        stats.put("clubsEnregistres", utilisateurRepository.countDistinctClubs());
        return ResponseEntity.ok(stats);
    }
  

    @GetMapping("/activities")
    public ResponseEntity<List<Activity>> getLastActivities() {
        List<Activity> activities = activityRepository.findTop5ByOrderByTimestampDesc();
        return ResponseEntity.ok(activities);
    }
}