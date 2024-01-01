package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.RatingFood;


@Repository
public interface RatingFoodRepository extends JpaRepository<RatingFood, Long> {
    // check Id exits
    boolean existsById(Long id);
}
