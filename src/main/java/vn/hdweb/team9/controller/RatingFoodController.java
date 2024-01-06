package vn.hdweb.team9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.hdweb.team9.domain.entity.RatingFood;
import vn.hdweb.team9.service.RatingFoodService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rating-food")
public class RatingFoodController {
    private final RatingFoodService ratingFoodService;
    /*
     * add new
     */
    @GetMapping("/add")
    public String addRatingForm(Model model) {
        return "ratingFood/add";
    }

    @PostMapping("/add")
    public String addRating(@RequestParam("userId") Long userId,
                            @RequestParam("foodId") Long foodId,
                            @RequestParam("content") String content,
                            @RequestParam("rateStar") int rateStar) {

        ratingFoodService.rateFood(userId, foodId, content, rateStar);
        return "index";
    }

    /*
     * list by food id
     */
    @GetMapping("/list/{foodId}")
    public String listRatings(@PathVariable("foodId") Long foodId, Model model) {
        List<RatingFood> ratings = ratingFoodService.getRatingsByFoodId(foodId);
        Double averageRating = ratingFoodService.calculateAverageRatingByFoodId(foodId);
        System.out.println(averageRating);
        model.addAttribute("ratings", ratings);
        model.addAttribute("averageRating", averageRating);

        return "ratingFood/list";
    }

    /*
     * delete
     */
    @GetMapping("/delete/{ratingId}")
    public String deleteRating(@PathVariable("ratingId") Long ratingId, @RequestParam("foodId") Long foodId) {
        ratingFoodService.deleteRating(ratingId);
        return "redirect:/rating-food/list/" + foodId;
    }
}
