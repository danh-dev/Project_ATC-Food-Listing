package vn.hdweb.team9.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hdweb.team9.domain.dto.CouponSearch;
import vn.hdweb.team9.domain.dto.RestaurantForm;
import vn.hdweb.team9.domain.dto.RestaurantSearch;
import vn.hdweb.team9.domain.dto.UpdateRestaurantForm;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.service.RestaurantService;


import java.util.List;

@Controller
@RequestMapping("/admin/restaurant")
@RequiredArgsConstructor
@Slf4j
public class AdminRestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping(value = {"", "/","/list"})
    public String restaurantsList(Model model) {

        List<Restaurant> restaurants = restaurantService.findRestaurants();
        /*order id user and reverse*/
//        users.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
        model.addAttribute("res", restaurants);
        return "admin/restaurant/restaurantList";
    }

    @GetMapping("/create")
    public String createRestaurant(Model model) {
//        log.info("in get create");
        RestaurantForm restaurantForm = new RestaurantForm();
        model.addAttribute("restaurantForm", restaurantForm);

        return "admin/restaurant/restaurantCreate";
    }

    @PostMapping("/create")
    public String create(@Valid RestaurantForm restaurantForm,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) throws FileUploadException {
        //og.info("in post create" + restaurantForm);
        if (result.hasErrors()) {
            return "admin/restaurant/restaurantCreate";
        }

        restaurantService.add(restaurantForm);

        return "redirect:/admin/restaurant/list";
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

        return "admin/restaurant/restaurantEdit";
    }

    @PostMapping("/update/{restaurantId}")
    public String updateRestaurant(@PathVariable("restaurantId") Long restaurantId,
                                   UpdateRestaurantForm updatedRestaurantForm)
                                        throws FileUploadException {
        // Gọi service để cập nhật thông tin nhà hàng
        restaurantService.update(restaurantId, updatedRestaurantForm);

        return "redirect:/admin/restaurant/list";
    }
}
