package tn.esprit.natationproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.natationproject.Entite.Comment;
import tn.esprit.natationproject.Entite.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<Event> findByName(String name);

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.categories")
    List<Event> findAllWithCategories();

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.categories c LEFT JOIN FETCH c.results WHERE e.id = :id")
    Optional<Event> findByIdWithCategoriesAndResults(Integer id);
}
