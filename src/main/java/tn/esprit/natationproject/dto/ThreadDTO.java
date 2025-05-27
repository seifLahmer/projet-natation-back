package tn.esprit.natationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.natationproject.Entite.Utilisateurs;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThreadDTO {
    private Long id;
    private String title;
    private Utilisateurs author;
    private List<Long> postIds;

}