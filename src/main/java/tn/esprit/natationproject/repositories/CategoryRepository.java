package tn.esprit.natationproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.natationproject.Entite.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByEventId(Integer eventId);

    Optional<Category> findByEventIdAndName(Integer eventId, String name);

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.results WHERE c.id = :id")
    Optional<Category> findByIdWithResults(@Param("id") Integer id);

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.event WHERE c.id = :id")
    Optional<Category> findByIdWithEvent(@Param("id") Integer id);
}
