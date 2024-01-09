package vn.hdweb.team9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.service.FoodService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ClientController {
    private final FoodService foodService;

    public ClientController(FoodService foodService) {
        this.foodService = foodService;
    }


    @GetMapping(value = {"", "/","/home","/index","/trang-chu","index.html","home.html","trang-chu.html"})
    public String home(Model model) {
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

    @GetMapping("/food_demo")
    public String food_demo() {
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
