package vn.hdweb.team9.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.service.RestaurantService;

import java.util.List;

@Controller
@RequestMapping("/admin/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/list")
    public String restaurantList(Model model) {
        List<Restaurant> restaurants = restaurantService.findRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "admin/restaurant/restaurantList";
    }

    @GetMapping("/new")
    public String createRestaurant() {
        return "admin/restaurant/createRestaurant";
    }

    @PostMapping("/new")
    public String create(RestaurantForm restaurantForm) {
        Restaurant restaurant = new Restaurant();

        restaurant.setRestaurantName(restaurantForm.getRestaurantName());
        restaurant.setSlug(restaurantForm.getSlug());
        restaurant.setDescription(restaurantForm.getDescription());
        restaurant.setAddress(restaurantForm.getAddress());
        restaurant.setLogo(restaurantForm.getLogo());
        restaurant.setImage(restaurantForm.getImage());
        restaurant.setOpenTime(restaurantForm.getOpenTime());
        restaurant.setCloseTime(restaurantForm.getCloseTime());
        restaurant.setCreatedAt(restaurantForm.getCreatedAt());
        restaurant.setActive(restaurantForm.isActive());
        restaurant.setListFood(restaurantForm.getListFood());
        restaurant.setListOrder(restaurantForm.getListOrder());
        restaurant.setListRatingRestaurant(restaurantForm.getListRatingRestaurant());

        restaurantService.add(restaurant);

        return "redirect:/admin";
    }

}
