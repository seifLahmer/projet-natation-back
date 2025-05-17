package tn.esprit.natationproject.Entite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "Inscription")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInscription;



    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnoreProperties("inscriptions")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idCompetition")
    private Competition competition;

    // Vous pouvez ajouter d'autres attributs spécifiques à l'inscription
    private Date dateInscription;

    @Enumerated(EnumType.STRING)
    private StatutInscription statut;
}
