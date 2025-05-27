package tn.esprit.natationproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.natationproject.Entite.Competition;
import tn.esprit.natationproject.Entite.Inscription;
import tn.esprit.natationproject.Entite.Utilisateurs;

import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Integer> {
   // List<Inscription> findInscriptionByUtilisateur_IdUtilisateur(int idUtilisateur);
   // List<Inscription> findInscriptionByUser_Id(Long idUser);
    List<Inscription> findInscriptionByUtilisateurs_Id(Long idUtilisateur);
    List<Inscription> findInscriptionByCompetition_IdReservation(int idReservation);

    // Alternative avec une requête JPQL si vous préférez
    @Query("SELECT i FROM Inscription i WHERE i.competition.idReservation = :competitionId")
    List<Inscription> findInscriptionsByCompetitionId(@Param("competitionId") int competitionId);

    // Requête directe pour récupérer les utilisateurs d'une compétition
    @Query("SELECT i.utilisateurs FROM Inscription i WHERE i.competition.idReservation = :competitionId")
    List<Utilisateurs> findUtilisateursByCompetitionId(@Param("competitionId") int competitionId);

}

