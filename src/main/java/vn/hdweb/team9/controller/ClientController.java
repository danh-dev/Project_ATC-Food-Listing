package vn.hdweb.team9.controller;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.service.CouponService;
import vn.hdweb.team9.service.RestaurantService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@Slf4j
public class ClientController {

    private final RestaurantService restaurantService;
    private final CouponService couponService;

    public ClientController(RestaurantService restaurantService, CouponService couponService) {
        this.restaurantService = restaurantService;
        this.couponService = couponService;
    }


    @GetMapping(value = {"", "/","/home","/index","/trang-chu","index.html","home.html","trang-chu.html"})
    public String home(Model model) {
        // coupons
        List<Coupon> coupons = couponService.findAll();
        Collections.shuffle(coupons);
        model.addAttribute("coupons", coupons);
        // restaurants
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
        log.info("slug la:" + slug);

        Optional<Restaurant> restaurant = restaurantService.findBySlug(slug);

        log.info("slug la:" + restaurant.get());
        model.addAttribute("restaurant", restaurant.get());
        return "client/restaurant_page";
    }
    @GetMapping("/food_demo")
    public String food_demo() {
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
