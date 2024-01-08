package vn.hdweb.team9.controller.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.domain.entity.Order;
import vn.hdweb.team9.domain.entity.RatingRestaurant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class RestaurantForm {
    private String restaurantName;
    private String slug;
    private String description;
    private String address;
    private MultipartFile image;
    private String openTime;
    private String closeTime;
    //private boolean isActive = true;
}
