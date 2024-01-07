package vn.hdweb.team9.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Long add(Restaurant restaurant) {
        restaurant = new Restaurant();

        // check duplicate name user
        validateDuplicateMember(restaurant);

        // if not duplication, saving member
        restaurantRepository.save(restaurant);

        // return the id of member
        return restaurant.getId();
    }

    private void validateDuplicateMember(Restaurant restaurant) {
        restaurantRepository.findByName(restaurant.getRestaurantName()).ifPresent(m -> {
            throw new IllegalStateException("This restaurant already exists.");
        });
    }

    public List<Restaurant> findRestaurants() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> findOne(Long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }
}
