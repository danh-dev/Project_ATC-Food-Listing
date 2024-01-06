package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.RatingRestaurant;
import vn.hdweb.team9.domain.entity.Restaurant;

import java.util.List;

@Repository
public interface RatingRestaurantRepository extends JpaRepository<RatingRestaurant, Long> {

    List<RatingRestaurant> findByRestaurantId(Long restaurantId);

    @Query("SELECT AVG(rr.rateStar) FROM RatingRestaurant rr WHERE rr.restaurant.id = :restaurantId")
    Double calAVGRatingByRestaurantId(@Param("restaurantId") Long restaurantId);
}

