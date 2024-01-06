package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.hdweb.team9.domain.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
