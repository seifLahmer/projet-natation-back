package tn.esprit.natationproject.Entite;


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
@Table(name = "centre")
@Getter
@Setter
@ToString(exclude = "piscines")
@NoArgsConstructor
public class Centre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCentre;

    @Column(name = "nomCentre")
    private String nomCentre;

    @Column(name = "localisation")
    private String localisation;

    @OneToMany(mappedBy = "centre" , fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Piscine> piscines;

}
