package org.example.backend.dto;//package org.example.backend.dto;

import org.example.backend.model.Comment;
import org.example.backend.model.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.model.Utilisateur;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private Utilisateur author;
    private Long threadId;
    private String content;
    private LocalDateTime createdAt;
    private List<Comment> comments;

}