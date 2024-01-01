package vn.hdweb.team9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.hdweb.team9.domain.entity.RatingFood;
import vn.hdweb.team9.service.RatingFoodService;

import java.util.List;
import java.util.Optional;

//@Controller
@RestController
@RequestMapping("/rating-food")
public class RatingFoodController {
    private final RatingFoodService ratingFoodService;

    @Autowired
    public RatingFoodController(RatingFoodService ratingFoodService) {
        this.ratingFoodService = ratingFoodService;
    }


    @PostMapping("/create")
    public ResponseEntity<RatingFood> createRatingFood(@RequestBody RatingFood ratingFood) {
        RatingFood createdRatingFood = ratingFoodService.createRatingFood(ratingFood);
        return new ResponseEntity<>(createdRatingFood, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingFood> getRatingFoodById(@PathVariable Long id) {
        Optional<RatingFood> ratingFood = ratingFoodService.getRatingFoodById(id);
        return ratingFood.map(food -> new ResponseEntity<>(food, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RatingFood>> getAllRatingFoods() {
        List<RatingFood> ratingFoods = ratingFoodService.getAllRatingFood();
        return new ResponseEntity<>(ratingFoods, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingFood> updateRatingFood(@PathVariable Long id, @RequestBody RatingFood updatedRatingFood) {
        RatingFood updatedFood = ratingFoodService.updateRatingFood(id, updatedRatingFood);
        if (updatedFood != null) {
            return new ResponseEntity<>(updatedFood, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRatingFoodById(@PathVariable Long id) {
        ratingFoodService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
