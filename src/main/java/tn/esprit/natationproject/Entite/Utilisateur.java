package tn.esprit.natationproject.Entite;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "utilisateurs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String nom;

    @Column(nullable = true)
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String telephone;

    @Column(name = "document_path")
    private String documentPath;

    @Column(nullable = false)
    private boolean active = false;

    @Column(name = "date_creation", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateCreation;

    @Column(nullable = false)
    private String role = "CHEF_EQUIPE";

    @Column(name = "nom_club", nullable = true)
    private String nomClub;

    @Column(name = "adresse_club", nullable = true)
    private String adresseClub;

    public String getFormattedDateCreation() {
        return dateCreation != null ?
                dateCreation.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) :
                "Date non disponible";
    }
}