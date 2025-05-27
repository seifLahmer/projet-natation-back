package tn.esprit.natationproject.repositories;

import tn.esprit.natationproject.Entite.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Club findByNomClub(String nomClub);
}