package vn.hdweb.team9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.hdweb.team9.domain.entity.RatingRestaurant;
import vn.hdweb.team9.service.RatingRestaurantService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/rating-restaurant")
public class RatingRestaurantController {
    private final RatingRestaurantService ratingRestaurantService;

    @Autowired
    public RatingRestaurantController(RatingRestaurantService ratingRestaurantService) {
        this.ratingRestaurantService = ratingRestaurantService;
    }

    @PostMapping("/create")
    public RatingRestaurant createRatingRestaurant(@RequestBody RatingRestaurant ratingRestaurant) {
        return ratingRestaurantService.createRatingRestaurant(ratingRestaurant);
    }

    @GetMapping("/{id}")
    public Optional<RatingRestaurant> getRatingRestaurantById(@PathVariable Long id) {
        return ratingRestaurantService.getRatingRestaurantById(id);
    }
    @GetMapping("/all")
    public List<RatingRestaurant> getAllRatingRestaurants() {
        return ratingRestaurantService.getAllRatingRestaurants();
    }

    @PutMapping("/{id}")
    public RatingRestaurant updateRatingRestaurant(@PathVariable Long id, @RequestBody RatingRestaurant updatedRatingRestaurant) {
        return ratingRestaurantService.updateRatingRestaurant(id, updatedRatingRestaurant);
    }

    @DeleteMapping("/{id}")
    public void deleteRatingRestaurantById(@PathVariable Long id) {
        ratingRestaurantService.deleteRatingRestaurantById(id);
    }


}
