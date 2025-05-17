package tn.esprit.natationproject.Entite;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clubs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nomClub;
    private String adresseClub;
    private String telephone;
}