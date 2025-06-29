package tn.esprit.natationproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.natationproject.Entite.Utilisateurs;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateursRepository extends JpaRepository<Utilisateurs, Long> {
    Utilisateurs findByEmail(String email);
    boolean existsByEmail(String email);
     List<Utilisateurs> findByRoleAndNomClubIsNull(String role);

    @Query("SELECT u FROM Utilisateurs u WHERE u.active = false AND u.role = :role")
    List<Utilisateurs> findByActiveFalseAndRole(@Param("role") String role);

    @Query("SELECT COUNT(u) FROM Utilisateurs u WHERE u.active = true AND u.role = :role")
    Long countByActiveTrueAndRole(@Param("role") String role);

    @Query("SELECT COUNT(DISTINCT u.nomClub) FROM Utilisateurs u WHERE u.nomClub IS NOT NULL")
    Long countDistinctClubs();
    // Dans UtilisateurRepository.java
    List<Utilisateurs> findByRoleAndNomClub(String role, String nomClub);

    @Query("SELECT i.utilisateurs FROM Inscription i WHERE i.competition.idReservation = (" +
            "SELECT ins.competition.idReservation FROM Inscription ins WHERE ins.idInscription = :idInscription)")
    List<Utilisateurs> findUtilisateursB(Integer idInscription);
} 