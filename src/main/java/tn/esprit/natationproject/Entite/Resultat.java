package tn.esprit.natationproject.Entite;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "Resultat")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Resultat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idResultat;
    private int place;

    @Column(length = 20)
    private String temps; // format : "00:58.45" (ou selon format chrono utilisé)

    @Column
    private  int points;

    @Column(name = "temps_de_passage", length = 100)
    private LocalTime tempsDePassage;

    @ManyToOne
    @JsonIgnoreProperties("resultats")
    private Competition competition;

    @ManyToOne
    @JsonIgnoreProperties("resultats")
    private Utilisateurs utilisateurs;
}
