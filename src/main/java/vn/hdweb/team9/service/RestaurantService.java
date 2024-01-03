package vn.hdweb.team9.service;

import org.springframework.stereotype.Service;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.repository.RestaurantRepository;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> findRestaurants() {
        return restaurantRepository.findAll();
    }


}

