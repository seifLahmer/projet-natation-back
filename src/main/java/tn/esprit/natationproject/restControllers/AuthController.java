package tn.esprit.natationproject.restControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.natationproject.Entite.RegisterRequest;
import tn.esprit.natationproject.Entite.Utilisateur;
import tn.esprit.natationproject.repositories.UtilisateurRepository;
import tn.esprit.natationproject.services.FileStorageService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<?> registerUser(@ModelAttribute RegisterRequest request) {
        try {
            if (utilisateurRepository.existsByEmail(request.getEmail())) {
                return ResponseEntity.badRequest().body("Email déjà utilisé");
            }

            if (!request.getPassword().equals(request.getConfirmPassword())) {
                return ResponseEntity.badRequest().body("Les mots de passe ne correspondent pas");
            }

            Utilisateur user = new Utilisateur();
            user.setNom(request.getNom());
            user.setPrenom(request.getPrenom());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setTelephone(request.getTelephone());
            user.setNomClub(request.getNomClub());
            user.setAdresseClub(request.getAdresseClub());
            user.setDateCreation(LocalDateTime.now());
            user.setRole("CHEF_EQUIPE");
            user.setActive(false);

            if (request.getDocumentJustificatif() != null
                    && !request.getDocumentJustificatif().isEmpty()) {
                String filename = fileStorageService.store(request.getDocumentJustificatif());
                user.setDocumentPath(filename);
            }

            utilisateurRepository.save(user);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Inscription réussie. En attente de validation.");

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erreur lors du traitement du fichier");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'inscription: " + e.getMessage());
        }
    }
}