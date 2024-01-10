package vn.hdweb.team9.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hdweb.team9.domain.dto.respon.RatingFoodDto;
import vn.hdweb.team9.domain.dto.respon.RatingRestaurantDto;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.domain.entity.RatingFood;
import vn.hdweb.team9.domain.entity.RatingRestaurant;
import vn.hdweb.team9.service.*;
import vn.hdweb.team9.domain.dto.respon.BlogResponDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class ClientController {
    private final RatingFoodService ratingFoodService;
    private final RatingRestaurantService ratingRestaurantService;
    private final BlogService blogService;
    private final CouponService couponService;
    private final FoodService foodService;

    @GetMapping(value = {"", "/","/home","/index","/trang-chu","index.html","home.html","trang-chu.html"})
    public String home(Model model) {
        List<BlogResponDto> blogs = blogService.findLimitOrderDate();
        model.addAttribute("blogs",blogs);
        List<Coupon> coupons = couponService.findAll();
        Collections.shuffle(coupons);
        model.addAttribute("coupons", coupons);
        List<RatingRestaurantDto> ratingList= ratingRestaurantService.findAll();
        model.addAttribute("ratings", ratingList);
        List<Food> foods = foodService.getAllFoods();
        model.addAttribute("foods", foods);
        return "client/index";
    }

    @GetMapping("/category_demo")
    public String category_demo() {
        return "client/category_page";
    }

    @GetMapping("/restaurant_demo")
    public String restaurant_demo() {
        return "client/restaurant_page";
    }

    @GetMapping("/restaurant_demo/{restaurantId}")
    public String restaurant_demo_detail(@PathVariable("restaurantId") Long restaurantId, Model model) {
        List<RatingRestaurantDto> ratingList = ratingRestaurantService.getRatingsByRestaurantId(restaurantId);
        Double ratingValue = ratingRestaurantService.calculateAverageRatingByRestaurantId(restaurantId);
        long countRating=ratingRestaurantService.countRating(restaurantId);

        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("ratingList", ratingList);
        model.addAttribute("ratingValue", ratingValue);
        model.addAttribute("countRating", countRating);

        return "client/restaurant_page";
    }


    @GetMapping("/food_demo")
    public String food_demo() {
        return "client/food_page";
    }


//    ratingFood detail by id
    @GetMapping("/food_demo/{foodId}")
    public String food_demo_detail(@PathVariable("foodId") Long foodId, Model model) {
        List<RatingFoodDto> ratingList = ratingFoodService.getRatingsByFoodId(foodId);
        Double ratingValue = ratingFoodService.calculateAverageRatingByFoodId(foodId);
        long countRating=ratingFoodService.countRating(foodId);
        log.info("Check rating list"+ ratingList);
        log.info("Check rating value"+ ratingValue);
        log.info("Check count" + countRating);

        model.addAttribute("foodId", foodId);
        model.addAttribute("ratingList", ratingList);
        model.addAttribute("ratingValue", ratingValue);
        model.addAttribute("countRating", countRating);
        return "client/food_page";
    }

    @GetMapping("/food/{slug}")
    public String getFoodDetail(Model model, @PathVariable("slug") String slug) {
        Optional<Food> food = foodService.findBySlug(slug);
        model.addAttribute("food", food.get());
        return "client/food_page";
    }

    @GetMapping("/post_demo")
    public String post_demo() {
        return "client/post_page";
    }

    @RequestMapping("/404")
    public String notFound() {
        return "client/404";
    }
}
