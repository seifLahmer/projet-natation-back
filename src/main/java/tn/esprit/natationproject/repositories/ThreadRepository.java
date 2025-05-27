package tn.esprit.natationproject.repositories;
import tn.esprit.natationproject.Entite.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
}