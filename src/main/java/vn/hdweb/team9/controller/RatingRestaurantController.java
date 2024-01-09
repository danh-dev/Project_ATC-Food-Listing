package vn.hdweb.team9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.hdweb.team9.domain.dto.respon.RatingRestaurantDto;
import vn.hdweb.team9.domain.entity.RatingRestaurant;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.repository.interfaces.RestaurantRepository;
import vn.hdweb.team9.service.RatingRestaurantService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rating-restaurant")
public class RatingRestaurantController {
    private final RatingRestaurantService ratingRestaurantService;

    /*
     * add new
     */
    @GetMapping("/add")
    public String addRating(Model model) {
        return "client/restaurant_page";
    }

    @PostMapping("/add")
    public String addRating(
// @RequestParam("userId") Long userId,
            @RequestParam("restaurantId") Long restaurantId,
            @RequestParam("content") String content,
            @RequestParam("rateStar") int rateStar,
            Model model) {

        //Test
        model.addAttribute("restaurantId", 1);

        ratingRestaurantService.rateRestaurant(restaurantId, content, rateStar);
        return "redirect:/";


    }

    /*
     * list by restaurant id
     */
    @GetMapping("/list/{restaurantId}")
    public String listRatings(@PathVariable("restaurantId") Long restaurantId, Model model) {
        List<RatingRestaurantDto> ratingList = ratingRestaurantService.getRatingsByRestaurantId(restaurantId);
        Double ratingValue = ratingRestaurantService.calculateAverageRatingByRestaurantId(restaurantId);
        long countRating = ratingRestaurantService.countRating(restaurantId);

        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("ratingList", ratingList);
        model.addAttribute("ratingValue", ratingValue);
        model.addAttribute("countRating", countRating);


        return "client/restaurant_page";


    }

    /*
     * rating by restaurantId and userId
     */
    @GetMapping("/detail/{restaurantId}/{userId}")
    public String ratingByRestaurantIdAndUserId(@PathVariable("restaurantId") Long restaurantId,
                                                @PathVariable("userId") Long userId,
                                                Model model) {
        Optional<RatingRestaurant> rating = ratingRestaurantService.getRatingByRestaurantIdAndUserId(restaurantId, userId);
        model.addAttribute("rating", rating.orElse(null));
        return "ratingRestaurant/detail";

    }

    /*
     * delete
     */
    @GetMapping("/delete/{ratingId}")
    public String deleteRating(@PathVariable("ratingId") Long ratingId, @RequestParam("restaurantId") Long restaurantId) {
        ratingRestaurantService.deleteRating(ratingId);
        return "redirect:/rating-restaurant/list/" + restaurantId;
    }
}
