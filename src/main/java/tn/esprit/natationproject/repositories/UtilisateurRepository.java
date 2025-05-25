package tn.esprit.natationproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.natationproject.Entite.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    boolean existsByEmail(String email);
    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findByRoleAndNomClubIsNull(String role);

    @Query("SELECT u FROM Utilisateur u WHERE u.active = false AND u.role = :role")
    List<Utilisateur> findByActiveFalseAndRole(@Param("role") String role);

    @Query("SELECT COUNT(u) FROM Utilisateur u WHERE u.active = true AND u.role = :role")
    Long countByActiveTrueAndRole(@Param("role") String role);

    @Query("SELECT COUNT(DISTINCT u.nomClub) FROM Utilisateur u WHERE u.nomClub IS NOT NULL")
    Long countDistinctClubs();
    // Dans UtilisateurRepository.java
    List<Utilisateur> findByRoleAndNomClub(String role, String nomClub);


} 