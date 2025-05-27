package tn.esprit.natationproject.restControllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.natationproject.Entite.Competition;
import tn.esprit.natationproject.Entite.Inscription;
import tn.esprit.natationproject.Entite.Utilisateurs;
import tn.esprit.natationproject.services.CompetitionService;
import tn.esprit.natationproject.services.ICompetitionService;
import tn.esprit.natationproject.services.IInscriptionService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/competitions")

@Tag(name = "gestion des competitions")

@AllArgsConstructor
public class CompetitionRestController {

    private ICompetitionService competitionService;
    private IInscriptionService inscriptionService;
    @GetMapping
    public List<Competition> getAllCompetitions() {return competitionService.getAllCompetitions();}
    @GetMapping("/{id}")
    public Competition getCompetition(@PathVariable int id) {return competitionService.getCompetition(id);}

    @PostMapping
    public Competition  addCompetition(@RequestBody Competition competition) {return competitionService.addCompetition(competition);}

    @PutMapping
    public Competition updateCompetiition(@RequestBody Competition competition) {return competitionService.updateCompetition(competition);}

    @DeleteMapping("/{id}")
    public void deleteCompetition(@PathVariable int id) {competitionService.deleteCompetition(id);}

    // Nouvelle méthode pour récupérer les utilisateurs inscrits à une compétition
    @GetMapping("/{competitionId}/users")
    public ResponseEntity<List<Utilisateurs>> getUsersByCompetition(@PathVariable int competitionId) {
        try {
            List<Utilisateurs> registeredUsers = inscriptionService.getUtilisateursByCompetitionId(competitionId);
            return ResponseEntity.ok(registeredUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Méthode alternative pour récupérer les inscriptions complètes
    @GetMapping("/{competitionId}/inscriptions")
    public ResponseEntity<List<Inscription>> getInscriptionsByCompetition(@PathVariable int competitionId) {
        try {
            List<Inscription> inscriptions = inscriptionService.getInscriptionsByCompetitionId(competitionId);
            return ResponseEntity.ok(inscriptions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint pour vérifier si un utilisateur est inscrit à une compétition
    @GetMapping("/{competitionId}/users/{userId}/inscription-status")
    public ResponseEntity<Boolean> isUserRegistered(@PathVariable int competitionId, @PathVariable Long userId) {
        try {
            List<Utilisateurs> registeredUsers = inscriptionService.getUtilisateursByCompetitionId(competitionId);
            boolean isRegistered = registeredUsers.stream()
                    .anyMatch(user -> user.getId().equals(userId));
            return ResponseEntity.ok(isRegistered);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
//int idCompetition, int idPiscine