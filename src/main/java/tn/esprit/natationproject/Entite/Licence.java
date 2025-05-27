package tn.esprit.natationproject.Entite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity
@Table(name = "licence")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Licence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "num_licence", unique = true)
    private String numLicence;

    @Column(name = "nom_joueur", nullable = false)
    private String nomJoueur;

    @Column(name = "prenom_joueur", nullable = false)
    private String prenomJoueur;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column(name = "date_expiration")
    private LocalDateTime dateExpiration;

    @Column(name = "active")
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Utilisateurs utilisateur;

    @Column(name = "club")
    private String club;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "niveau")
    private String niveau;
}
