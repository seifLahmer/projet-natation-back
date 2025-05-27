package tn.esprit.natationproject.repositories;

import tn.esprit.natationproject.Entite.Centre;
import tn.esprit.natationproject.Entite.Piscine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PiscineRepository extends JpaRepository<Piscine, Integer> {
    List<Piscine> findPiscinesByCentre_IdCentre(int idCentre);
}
