package tn.esprit.natationproject.Entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "utilisateurs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Utilisateurs implements UserDetails {
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

    @OneToMany(mappedBy = "utilisateurs")
    @JsonIgnore
    private List<Inscription> inscriptions;

    @OneToMany(mappedBy = "utilisateurs")
    @JsonIgnore
    private List<Resultat> resultats;

    public String getFormattedDateCreation() {
        return dateCreation != null ?
                dateCreation.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) :
                "Date non disponible";
    }
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // ou mettre une logique si tu veux désactiver un compte après une date
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // ou mettre une logique de blocage
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // à adapter selon ta logique métier
    }

    @Override
    public boolean isEnabled() {
        return active; // ce champ peut être géré par l'admin
    }

}