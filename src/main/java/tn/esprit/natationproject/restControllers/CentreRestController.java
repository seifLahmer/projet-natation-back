package tn.esprit.natationproject.restControllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.natationproject.Entite.Centre;
import tn.esprit.natationproject.Entite.Piscine;
import tn.esprit.natationproject.services.ICentreService;
import tn.esprit.natationproject.services.PiscineService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/admin/centres")
@Tag(name = "gestion des centres")

@AllArgsConstructor
public class CentreRestController {

    private ICentreService centreService;
    private PiscineService piscineService;
    @GetMapping
    public List<Centre> getCentres() { return centreService.getCentres(); }

    @GetMapping("/{id}")
    public Centre getCentre(@PathVariable int id) { return centreService.getCentre(id); }

    @PostMapping
    public Centre addCentre(@RequestBody Centre centre) {return centreService.addCentre(centre); }

    @PutMapping
    public Centre updateCentre(@RequestBody Centre centre) {return centreService.updateCentre(centre); }

    @DeleteMapping("/{id}")
    public void deleteCentre(@PathVariable int id) { centreService.deleteCentre(id); }


}
