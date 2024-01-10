package vn.hdweb.team9.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    
    @GetMapping("/foods")
    public String getRestaurantByFoodName(@RequestParam("foodName") String foodName, Model model) {
        Set<Restaurant> restaurantSet = restaurantService.findAllRestaurantsWithFoodName(foodName);
        List<Restaurant> restaurants = new ArrayList<>(restaurantSet);
        model.addAttribute("restaurants", restaurants);
        return "client/restaurant_food";
    }
}
