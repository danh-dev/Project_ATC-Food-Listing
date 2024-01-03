package vn.hdweb.team9.repository.interfaces;

import vn.hdweb.team9.domain.entity.Restaurant;

import java.util.List;
import java.util.Optional;

public interface IRestaurantRepository {
    List<Restaurant> findAll();
    Optional<Restaurant> findById(Long id);
    Optional<Restaurant> findByFoodId (Long foodId);

}
