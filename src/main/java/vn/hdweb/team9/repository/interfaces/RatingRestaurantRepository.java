package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.RatingRestaurant;

@Repository
public interface RatingRestaurantRepository extends JpaRepository<RatingRestaurant, Long> {
}
