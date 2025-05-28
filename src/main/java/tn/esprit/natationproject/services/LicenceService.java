package tn.esprit.natationproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.esprit.natationproject.Entite.Licence;
import tn.esprit.natationproject.Entite.Utilisateurs;
import tn.esprit.natationproject.repositories.LicenceRepository;
import tn.esprit.natationproject.repositories.UtilisateursRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LicenceService {

    @Autowired
    private LicenceRepository licenceRepository;

    @Autowired
    private UtilisateursRepository utilisateursRepository;

    public Licence createLicence(Licence licence) {
        // Vérifier si l'utilisateur courant est un chef d'équipe
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateurs currentUser = (Utilisateurs) authentication.getPrincipal();
        
        if (!currentUser.getRole().equals("CHEF_EQUIPE")) {
            throw new RuntimeException("Seul un chef d'équipe peut créer une licence");
        }

        // Générer un numéro de licence unique
        licence.setNumLicence(generateLicenceNumber());
        
        // Vérifier si l'email existe déjà
        if (licenceRepository.existsByEmail(licence.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email possède déjà une licence");
        }

        // Définir la date d'expiration (1 an après la création)
        licence.setDateExpiration(LocalDateTime.now().plusYears(1));
        
        // Associer la licence à l'utilisateur
        licence.setUtilisateur(currentUser);

        return licenceRepository.save(licence);
    }

    public List<Licence> getAllLicences() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateurs currentUser = (Utilisateurs) authentication.getPrincipal();

        if (currentUser.getRole().equals("CHEF_EQUIPE")) {
            return licenceRepository.findAll();
        } else {
            return licenceRepository.findByUtilisateur(currentUser);
        }
    }

    public Licence getLicenceById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateurs currentUser = (Utilisateurs) authentication.getPrincipal();
        
        Licence licence = licenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Licence non trouvée"));

        // Vérifier si l'utilisateur a le droit de voir cette licence
        if (!currentUser.getRole().equals("CHEF_EQUIPE") && 
            !licence.getUtilisateur().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'avez pas le droit de voir cette licence");
        }

        return licence;
    }

    public Licence updateLicence(Long id, Licence licenceDetails) {
        Licence licence = licenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Licence non trouvée"));

        licence.setNomJoueur(licenceDetails.getNomJoueur());
        licence.setPrenomJoueur(licenceDetails.getPrenomJoueur());
        licence.setTelephone(licenceDetails.getTelephone());
        licence.setClub(licenceDetails.getClub());
        licence.setCategorie(licenceDetails.getCategorie());
        licence.setNiveau(licenceDetails.getNiveau());

        return licenceRepository.save(licence);
    }

    public void deleteLicence(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateurs currentUser = (Utilisateurs) authentication.getPrincipal();

        if (!currentUser.getRole().equals("CHEF_EQUIPE")) {
            throw new RuntimeException("Seul un chef d'équipe peut supprimer une licence");
        }

        Licence licence = licenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Licence non trouvée"));

        licenceRepository.delete(licence);
    }

    private String generateLicenceNumber() {
        // Générer un numéro de licence unique basé sur UUID
        return "LIC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public Licence getUserLicence() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateurs currentUser = (Utilisateurs) authentication.getPrincipal();
        
        return licenceRepository.findByEmail(currentUser.getEmail());
    }

    public boolean hasLicenceByEmail(String email) {
        return licenceRepository.existsByEmail(email);
    }

    public Licence getLicenceByEmail(String email) {
        Licence licence = licenceRepository.findByEmail(email);
        if (licence == null) {
            throw new RuntimeException("Aucune licence trouvée pour cet email");
        }
        return licence;
    }

    public Licence getLicenceByNumero(String numLicence) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateurs currentUser = (Utilisateurs) authentication.getPrincipal();
        
        Licence licence = licenceRepository.findByNumLicence(numLicence);
        if (licence == null) {
            throw new RuntimeException("Aucune licence trouvée avec ce numéro");
        }

        // Vérifier si l'utilisateur a le droit de voir cette licence
        if (!currentUser.getRole().equals("CHEF_EQUIPE") && 
            !licence.getUtilisateur().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'avez pas le droit de voir cette licence");
        }

        return licence;
    }

    public Licence updateLicenceByNumero(String numLicence, Licence licenceDetails) {
        Licence licence = licenceRepository.findByNumLicence(numLicence);
        if (licence == null) {
            throw new RuntimeException("Licence non trouvée avec le numéro : " + numLicence);
        }

        licence.setNomJoueur(licenceDetails.getNomJoueur());
        licence.setPrenomJoueur(licenceDetails.getPrenomJoueur());
        licence.setTelephone(licenceDetails.getTelephone());
        licence.setClub(licenceDetails.getClub());
        licence.setCategorie(licenceDetails.getCategorie());
        licence.setNiveau(licenceDetails.getNiveau());

        return licenceRepository.save(licence);
    }

    public void deleteLicenceByNumero(String numLicence) {
        Licence licence = licenceRepository.findByNumLicence(numLicence);
        if (licence == null) {
            throw new RuntimeException("Licence non trouvée avec le numéro : " + numLicence);
        }
        licenceRepository.delete(licence);
    }
} 