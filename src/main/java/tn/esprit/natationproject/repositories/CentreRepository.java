package tn.esprit.natationproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.natationproject.Entite.Centre;

@Repository
public interface CentreRepository extends JpaRepository<Centre, Integer> {
}
