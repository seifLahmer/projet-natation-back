package tn.esprit.natationproject.Entite;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Competition")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReservation;
    private int nbreAceptee ;
    @Column(name = "typeC")
    @Enumerated(EnumType.STRING)
    private typeCompetition typeC;

    @Column(name = "dateDebut")
    private Date dateDebut;

    @Column(name = "dateFin")
    private Date dateFin;

    @Column(name = "nbrParticipants")
    private int nbrParticipants;

    @Column(name = "heure")
    private LocalTime heure;

    @OneToMany(mappedBy = "competition")
    @JsonIgnore
    private List<Resultat> resultats;

    @ManyToOne
    @JsonIgnoreProperties("competitions")
    private Piscine piscine;

    @OneToMany(mappedBy = "competition")
    @JsonIgnore
    private List<Inscription> inscriptions;
}
