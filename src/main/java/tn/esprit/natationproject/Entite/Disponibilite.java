package tn.esprit.natationproject.Entite;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Disponibilite")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Disponibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDisponibilite;
}
