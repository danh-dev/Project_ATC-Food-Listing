package vn.hdweb.team9.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hdweb.team9.domain.dto.respon.RatingFoodDto;
import vn.hdweb.team9.domain.dto.respon.RatingRestaurantDto;
import vn.hdweb.team9.domain.entity.RatingFood;
import vn.hdweb.team9.domain.entity.RatingRestaurant;
import vn.hdweb.team9.service.RatingFoodService;
import vn.hdweb.team9.service.RatingRestaurantService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/rating-restaurant")
public class AdminRatingRestaurantController {
    private final RatingRestaurantService ratingRestaurantService;

    @GetMapping("/list")
    public String getList(Model model) {
        List<RatingRestaurantDto> ratingResponseList= ratingRestaurantService.findAll();

        model.addAttribute("title", "Rating Food List");
        model.addAttribute("ratingList", ratingResponseList);
        return "admin/ratingRestaurant/ratingList";
    }


    @PostMapping("/delete/{restaurantId}")
    public String deleteById(@PathVariable("restaurantId") Long restaurantId) {
        ratingRestaurantService.deleteRating(restaurantId);
        return "redirect:/admin/rating-restaurant/list";
    }
}