package vn.hdweb.team9.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.entity.RatingRestaurant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RatingRestaurantServiceTest {

    @Autowired
    private RatingRestaurantService ratingRestaurantService;

    @Test
    void createRatingRestaurant() {
        RatingRestaurant ratingRestaurant= new RatingRestaurant();
        ratingRestaurant.setContent("kdfsj");

    }

    @Test
    void getRatingRestaurantById() {
    }

    @Test
    void getAllRatingRestaurants() {
    }

    @Test
    void updateRatingRestaurant() {
    }

    @Test
    void deleteRatingRestaurantById() {
    }
}