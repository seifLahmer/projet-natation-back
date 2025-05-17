package tn.esprit.natationproject.restControllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.natationproject.Entite.Competition;
import tn.esprit.natationproject.services.CompetitionService;
import tn.esprit.natationproject.services.ICompetitionService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/competitions")
@Tag(name = "gestion des competitions")

@AllArgsConstructor
public class CompetitionRestController {

    private ICompetitionService competitionService;

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


}
//int idCompetition, int idPiscine