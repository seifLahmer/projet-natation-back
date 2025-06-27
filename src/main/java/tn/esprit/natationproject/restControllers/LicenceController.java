package tn.esprit.natationproject.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.natationproject.Entite.Licence;
import tn.esprit.natationproject.services.LicenceService;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/licences")
@CrossOrigin(origins = "http://localhost:4200")
public class LicenceController {

    @Autowired
    private LicenceService licenceService;

    @PostMapping
    public ResponseEntity<Licence> createLicence(@RequestBody Licence licence) {
        try {
            Licence newLicence = licenceService.createLicence(licence);
            return ResponseEntity.ok(newLicence);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Licence>> getAllLicences() {
        try {
            List<Licence> licences = licenceService.getAllLicences();
            return ResponseEntity.ok(licences);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/my-licence")
    public ResponseEntity<Licence> getUserLicence() {
        try {
            Licence licence = licenceService.getUserLicence();
            if (licence != null) {
                return ResponseEntity.ok(licence);
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/parmail/{email}")
    public ResponseEntity<?> checkLicenceByEmail(@PathVariable String email) {
        try {
            boolean hasLicence = licenceService.hasLicenceByEmail(email);
            Map<String, Object> response = new HashMap<>();
            response.put("hasLicence", hasLicence);
            if (hasLicence) {
                Licence licence = licenceService.getLicenceByEmail(email);
                response.put("numLicence", licence.getNumLicence());
                response.put("dateExpiration", licence.getDateExpiration());
                response.put("nomJoueur", licence.getNomJoueur());
                response.put("prenomJoueur", licence.getPrenomJoueur());
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/numero/{numLicence}")
    public ResponseEntity<?> getLicenceByNumero(@PathVariable String numLicence) {
        try {
            Licence licence = licenceService.getLicenceByNumero(numLicence);
            return ResponseEntity.ok(licence);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{numLicence}")
    public ResponseEntity<?> updateLicenceByNumero(
            @PathVariable String numLicence,
            @RequestBody Licence licenceDetails
    ) {
        try {
            Licence updatedLicence = licenceService.updateLicenceByNumero(numLicence, licenceDetails);
            return ResponseEntity.ok(updatedLicence);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{numLicence}")
    public ResponseEntity<?> deleteLicence(@PathVariable String numLicence) {
        try {
            licenceService.deleteLicenceByNumero(numLicence);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 