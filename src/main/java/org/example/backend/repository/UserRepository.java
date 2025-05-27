package org.example.backend.repository;

import org.example.backend.enumeration.Role;
import org.example.backend.model.Utilisateur;
import org.example.backend.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur,Long> {

    Optional<Utilisateur> findByEmail(String email);
    Utilisateur getUserByEmail(String email);

    List<Utilisateur> findUserByRole(Role role);





}
