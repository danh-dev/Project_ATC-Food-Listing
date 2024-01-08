package vn.hdweb.team9.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hdweb.team9.domain.dto.respon.RestaurantDto;
import vn.hdweb.team9.domain.dto.respon.UserDto;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.service.RestaurantService;


import java.util.List;

@Controller
@RequestMapping("/admin/restaurants")
@RequiredArgsConstructor
@Slf4j
public class AdminRestaurantController {
    private final RestaurantService restaurantService;

    @RequestMapping(value = {"", "/","/list"})
    public String restaurantsList(Model model) {

        List<Restaurant> restaurants = restaurantService.findRestaurants();
        /*order id user and reverse*/
//        users.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
        model.addAttribute("res", restaurants);
        return "admin/restaurants_list";
    }

    @GetMapping("/create")
    public String createRestaurant(Model model) {
        model.addAttribute("res", new RestaurantForm());
        log.info("in get create");
        return "admin/restaurants_create";
    }

    @PostMapping("/create")
    public String create(RestaurantForm restaurantForm) throws FileUploadException {
        //og.info("in post create" + restaurantForm);
        restaurantService.add(restaurantForm);

        return "redirect:/admin/restaurants/list";
    }
}
