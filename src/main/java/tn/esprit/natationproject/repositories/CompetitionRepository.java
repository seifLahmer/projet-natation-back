package tn.esprit.natationproject.repositories;

import tn.esprit.natationproject.Entite.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {


}
