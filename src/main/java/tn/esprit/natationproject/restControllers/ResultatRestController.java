package tn.esprit.natationproject.restControllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.natationproject.Entite.Resultat;
import tn.esprit.natationproject.Entite.typeCompetition;
import tn.esprit.natationproject.services.IResultatService;
import tn.esprit.natationproject.services.ResultatService;
import java.util.List;
@RestController
@RequestMapping("api/resultats")
@Tag(name = "gestion des resultats")
@AllArgsConstructor
public class ResultatRestController {

    private IResultatService resultatService;

    @GetMapping("/{id}")
    public Resultat getResultat(@PathVariable int id) {
        return resultatService.getResultat(id);
    }

    @PostMapping
    public Resultat addResultat(@RequestBody Resultat resultat) {
        return resultatService.addResultat(resultat);
    }

    @PutMapping("/{id}")
    public Resultat updateResultat(@RequestBody Resultat resultat) {
        return resultatService.updateResultat(resultat);
    }

    @DeleteMapping("/{id}")
    public void deleteResultat(@PathVariable int id) {
        resultatService.deleteResultat(id);
    }

    @GetMapping("/competition")
    public ResponseEntity<List<Resultat>> getResultatsParCompetition(
            @RequestParam typeCompetition typeC) {
        List<Resultat> resultats = resultatService.getResultatsParTypeCompetition(typeC);
        return ResponseEntity.ok(resultats);
    }

}