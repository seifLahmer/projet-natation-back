package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.model.Utilisateur;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThreadDTO {
    private Long id;
    private String title;
    private Utilisateur author;
    private List<Long> postIds;

}