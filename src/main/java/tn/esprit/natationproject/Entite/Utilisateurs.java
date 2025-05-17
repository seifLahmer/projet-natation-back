package tn.esprit.natationproject.Entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "utilisateurs") // Spécifie explicitement le nom de la table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Mapping explicite
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;
    // ajouter par seif
    @Column
    private LocalDate naissance;

    @Column(length = 50)
    private String nation;
    //
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

    // ajouter par seif
    @OneToMany(mappedBy = "utilisateurs")
    @JsonIgnore
    private List<Inscription> inscriptions;

    @OneToMany(mappedBy = "utilisateurs")
    @JsonIgnore
    private List<Resultat> resultats;
}
