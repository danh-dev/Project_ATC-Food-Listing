package vn.hdweb.team9.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hdweb.team9.domain.dto.respon.RatingFoodDto;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.domain.entity.RatingFood;
import vn.hdweb.team9.service.RatingFoodService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/rating-food")
public class AdminRatingFoodController {
    private final RatingFoodService ratingFoodService;

    @GetMapping("/list")
    public String getList(Model model) {
        List<RatingFoodDto> ratingResponseList = ratingFoodService.findAll();
        long count = ratingResponseList.size();
        double avgRate = ratingResponseList.stream().mapToDouble(RatingFoodDto::getRateStar)
                .average()
                .orElse(0.0);

        model.addAttribute("title", "Rating Food List");
        model.addAttribute("ratingList", ratingResponseList);
        model.addAttribute("count", count);
        model.addAttribute("avgRate", avgRate);
        return "admin/ratingFood/ratingList";
    }


    @PostMapping("/delete/{foodId}")
    public String deleteById(@PathVariable("foodId") Long foodId) {
        ratingFoodService.deleteRating(foodId);
        return "redirect:/admin/rating-food/list";
    }
}