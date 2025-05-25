// ActivityRepository.java
package tn.esprit.natationproject.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.natationproject.Entite.Activity;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findTop5ByOrderByTimestampDesc();
}