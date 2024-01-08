package vn.hdweb.team9.repository.interfaces;

import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRestaurantRepository {
    Restaurant save (Restaurant restaurant);
    Optional<Restaurant> findById(Long id);
    Optional<Restaurant> findByName(String restaurantName);
    List<Restaurant> findAll();
}
