package tn.esprit.natationproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.natationproject.Entite.Resultat;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.natationproject.Entite.typeCompetition;

import java.util.List;

public interface ResultatRepository extends JpaRepository<Resultat, Integer> {
    List<Resultat> findByCompetition_TypeC(typeCompetition typeC);
}
