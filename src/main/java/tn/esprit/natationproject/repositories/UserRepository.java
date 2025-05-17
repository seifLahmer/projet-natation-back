package tn.esprit.natationproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.natationproject.Entite.Resultat;
import tn.esprit.natationproject.Entite.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
