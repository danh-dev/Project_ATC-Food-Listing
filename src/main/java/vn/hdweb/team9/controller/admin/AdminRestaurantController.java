package vn.hdweb.team9.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/update/{restaurantId}")
    public String updateRestaurantForm(@PathVariable("restaurantId") Long restaurantId, Model model) {
        log.info("Run this one!");

        // Lấy thông tin nhà hàng cần cập nhật từ service
        Restaurant restaurant = restaurantService.findOne(restaurantId)
                .orElseThrow(() -> new RestaurantService
                        .RestaurantNotFoundException("Restaurant not found with id: "
                        + restaurantId));

        // Chuyển đổi đối tượng Restaurant thành đối tượng RestaurantForm để hiển thị trong form
        UpdateRestaurantForm updatedRestaurantForm = new UpdateRestaurantForm();
        BeanUtils.copyProperties(restaurant, updatedRestaurantForm);

        // Truyền đối tượng RestaurantForm vào model để hiển thị trong form
        model.addAttribute("res", updatedRestaurantForm);

        return "admin/restaurants_update";
    }

    @PostMapping("/update/{restaurantId}")
    public String updateRestaurant(@PathVariable("restaurantId") Long restaurantId,
                                   UpdateRestaurantForm updatedRestaurantForm)
                                        throws FileUploadException {
        // Gọi service để cập nhật thông tin nhà hàng
        restaurantService.update(restaurantId, updatedRestaurantForm);

        return "redirect:/admin/restaurants/list";
    }
}
