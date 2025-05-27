package tn.esprit.natationproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.natationproject.Entite.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
