package vn.hdweb.team9.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.dto.respon.RestaurantDto;
import vn.hdweb.team9.domain.entity.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRestaurantRepository extends JpaRepository<Restaurant, Long> {
    //Optional<Restaurant> findById(Long id);
    List<Restaurant> findByRestaurantName(String restaurantName);
    Restaurant findBySlug(String slug);

    //List<Restaurant> findAll();
}
