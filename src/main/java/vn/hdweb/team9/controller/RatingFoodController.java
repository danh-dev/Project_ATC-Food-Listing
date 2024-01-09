package vn.hdweb.team9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.hdweb.team9.domain.dto.respon.RatingFoodDto;
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
        return "client/food_page";
    }

    @PostMapping("/add")
    public String addRating(
//            @RequestParam("userId") Long userId,
                            @RequestParam("foodId") Long foodId,
                            @RequestParam("content") String content,
                            @RequestParam("rateStar") int rateStar,
                            Model model) {
//        test
        model.addAttribute("foodId",1);
        ratingFoodService.rateFood( foodId, content, rateStar);
        return "redirect:/";

    }

    /*
     * list by food id
     */
    @GetMapping("/list/{foodId}")
    public String listRatings(@PathVariable("foodId") Long foodId, Model model) {
        List<RatingFoodDto> ratingList = ratingFoodService.getRatingsByFoodId(foodId);
        Double ratingValue = ratingFoodService.calculateAverageRatingByFoodId(foodId);
        long countRating=ratingFoodService.countRating(foodId);

        model.addAttribute("foodId", foodId);
        model.addAttribute("ratingList", ratingList);
        model.addAttribute("ratingValue", ratingValue); //AVG rating
        model.addAttribute("countRating", countRating);
//        return "ratingFood/list";
        return "client/food_page";
    }

    /*
     * list by foodId and userId
     */
    @GetMapping("/detail/{foodId}/{userId}")
    public String ratingByFoodAndUser(@PathVariable("foodId") Long foodId, @PathVariable("userId") Long userId, Model model) {
        Optional<RatingFood> rating = ratingFoodService.getRatingByFoodIdAndUserId(foodId, userId);
        model.addAttribute("rating", rating.orElse(null));
        return "ratingFood/detail";
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
