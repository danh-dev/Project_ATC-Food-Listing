package vn.hdweb.team9.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/restaurant")
public class RestaurantController {
    @GetMapping("/list")
    public String restaurantList() {
        return "admin/restaurant/restaurantList";
    }

    @GetMapping("/new")
    public String createRestaurant() {
        return "admin/restaurant/createRestaurant";
    }
}
