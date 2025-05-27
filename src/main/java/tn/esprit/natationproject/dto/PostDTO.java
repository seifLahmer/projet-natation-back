package tn.esprit.natationproject.dto;//package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.natationproject.Entite.Comment;
import tn.esprit.natationproject.Entite.Utilisateurs;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private Utilisateurs author;
    private Long threadId;
    private String content;
    private LocalDateTime createdAt;
    private List<Comment> comments;

}