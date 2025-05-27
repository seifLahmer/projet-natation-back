package tn.esprit.natationproject.repositories;

import tn.esprit.natationproject.Entite.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findByCategoryId(Integer categoryId);

    List<Result> findByNation(String nation);

    List<Result> findByClub(String club);

    @Query("SELECT r FROM Result r WHERE r.namePrenom LIKE %:name%")
    List<Result> findByNameContaining(@Param("name") String name);

    @Query("SELECT r FROM Result r JOIN r.category c WHERE c.event.id = :eventId")
    List<Result> findByEventId(@Param("eventId") Integer eventId);

    @Query("SELECT r FROM Result r LEFT JOIN FETCH r.category c LEFT JOIN FETCH c.event WHERE r.id = :id")
    Result findByIdWithCategoryAndEvent(@Param("id") Integer id);

    @Query("SELECT r FROM Result r JOIN r.category c WHERE c.event.id = :eventId AND c.id = :categoryId ORDER BY r.place")
    List<Result> findByEventIdAndCategoryIdOrderByPlace(@Param("eventId") Integer eventId, @Param("categoryId") Integer categoryId);
}