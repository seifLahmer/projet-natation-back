package org.example.backend.model;
// src/main/java/tn/esprit/natationproject/Entite/Utilisateur.java


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "utilisateurs") // Spécifie explicitement le nom de la table
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Mapping explicite
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "document_path")
    private String document_path;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime date_creation;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "club_id")
    private Long club_id;
    @Column(name = "nom_club")
    private String nomClub;

    @Column(name = "adresse_club")
    private String adresseClub;
}

