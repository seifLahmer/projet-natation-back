package tn.esprit.natationproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.natationproject.Entite.Utilisateurs;
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateurs, Long> {
}
