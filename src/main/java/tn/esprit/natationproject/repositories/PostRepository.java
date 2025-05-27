package tn.esprit.natationproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.natationproject.Entite.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}