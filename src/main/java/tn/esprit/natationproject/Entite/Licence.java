package tn.esprit.natationproject.Entite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Licence")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Licence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numLicence;
}
