package tn.esprit.natationproject.restControllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.natationproject.Entite.Piscine;
import tn.esprit.natationproject.services.IPiscineService;
import tn.esprit.natationproject.services.PiscineService;

import java.util.List;

@RestController
@RequestMapping("api/piscines")

@Tag(name = "gestion des piscines")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class PiscineRestController {

    private IPiscineService piscineService;
    @GetMapping
    public List<Piscine> getAllPiscines() {
        return piscineService.getPiscines();
    }
    @GetMapping("/{id}")
    public Piscine getPiscine(@PathVariable int id) {return  piscineService.getPiscine(id);}

    @PostMapping
    public Piscine addPiscine(@RequestBody Piscine piscine) {return piscineService.addPiscine(piscine);}

    @PutMapping("/{id}")
    public Piscine updatePiscine(@RequestBody Piscine piscine) {return piscineService.updatePiscine(piscine);}

    @DeleteMapping("/{id}")
    public void deletePiscine(@PathVariable int id) {piscineService.deletePiscine(id);
    }
    @GetMapping("/centres/{idCentre}/piscines")
    public ResponseEntity<List<Piscine>> getPiscinesParCentre(@PathVariable int idCentre) {
        List<Piscine> piscines = piscineService.getPiscinesParCentre(idCentre);
        return ResponseEntity.ok(piscines);
    }

}
