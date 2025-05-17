package tn.esprit.natationproject.Entite;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "piscine")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Piscine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPiscine;

    @Column(name = "nomPiscine")
    private String nomPiscine;

    @Column(name = "nbreRows")
    private int nbreRows;

    @OneToMany(mappedBy = "piscine")
    @JsonIgnore
    private List<Competition> competitions;

    @ManyToOne
    @JsonIgnoreProperties("piscines")
    private Centre centre;

}
