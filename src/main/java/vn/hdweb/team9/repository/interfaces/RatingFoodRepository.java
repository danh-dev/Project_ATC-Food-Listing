package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.domain.entity.RatingFood;

import java.util.List;


@Repository
public interface RatingFoodRepository extends JpaRepository<RatingFood, Long> {
    List<RatingFood> findByFoodId(Long foodId);

    @Query("SELECT AVG(rf.rateStar) FROM RatingFood rf WHERE rf.food.id = :foodId")
    Double calculateAverageRatingByFoodId(@Param("foodId") Long foodId);

}
