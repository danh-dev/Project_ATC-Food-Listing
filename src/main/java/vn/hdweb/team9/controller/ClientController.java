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
import vn.hdweb.team9.domain.entity.*;
import vn.hdweb.team9.service.*;
import vn.hdweb.team9.domain.dto.respon.BlogResponDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    private final RestaurantService restaurantService;

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
        List<Restaurant> restaurants = restaurantService.findRestaurants();
        model.addAttribute("restaurants",restaurants);
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



    @GetMapping("/restaurant/{slug}")
    public String getRestaurantDetail(Model model, @PathVariable("slug") String slug) {

        Optional<Restaurant> restaurant = restaurantService.findBySlug(slug);

        List<RatingRestaurantDto> ratingList = ratingRestaurantService.getRatingsByRestaurantId(restaurant.get().getId());
        Double ratingValue = ratingRestaurantService.calculateAverageRatingByRestaurantId(restaurant.get().getId());
        long countRating=ratingRestaurantService.countRating(restaurant.get().getId());

        model.addAttribute("restaurantId", restaurant.get().getId());
        model.addAttribute("ratingList", ratingList);
        model.addAttribute("ratingValue", ratingValue);
        if(ratingValue == null) {
            model.addAttribute("ratingValue", 0);
        }
        model.addAttribute("countRating", countRating);

        model.addAttribute("restaurant", restaurant.get());
        return "client/restaurant_page";
    }
    @GetMapping("/food_demo")
    public String food_demo() {
        return "client/food_page";
    }

    @GetMapping("/food/{slug}")
    public String getFoodDetail(Model model, @PathVariable("slug") String slug) {
        Optional<Food> food = foodService.findBySlug(slug);

        List<RatingFoodDto> ratingList = ratingFoodService.getRatingsByFoodId(food.get().getId());
        Double ratingValue = ratingFoodService.calculateAverageRatingByFoodId(food.get().getId());
        long countRating=ratingFoodService.countRating(food.get().getId());

        model.addAttribute("foodId", food.get().getId());
        model.addAttribute("ratingList", ratingList);
        model.addAttribute("ratingValue", ratingValue);
        if(ratingValue == null) {
            model.addAttribute("ratingValue", 0);
        }
        model.addAttribute("countRating", countRating);
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
