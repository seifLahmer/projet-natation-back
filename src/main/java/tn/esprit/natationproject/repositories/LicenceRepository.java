package tn.esprit.natationproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.natationproject.Entite.Licence;
import tn.esprit.natationproject.Entite.Utilisateurs;
import java.util.List;

@Repository
public interface LicenceRepository extends JpaRepository<Licence, Long> {
    List<Licence> findByUtilisateur(Utilisateurs utilisateur);
    boolean existsByEmail(String email);
    Licence findByEmail(String email);
    Licence findByNumLicence(String numLicence);
} 