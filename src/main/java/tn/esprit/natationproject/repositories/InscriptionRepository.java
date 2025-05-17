package tn.esprit.natationproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.natationproject.Entite.Competition;
import tn.esprit.natationproject.Entite.Inscription;

import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Integer> {
   // List<Inscription> findInscriptionByUtilisateur_IdUtilisateur(int idUtilisateur);
   // List<Inscription> findInscriptionByUser_Id(Long idUser);
    List<Inscription> findInscriptionByUtilisateurs_Id(Long idUtilisateur);

}

