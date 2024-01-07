package vn.hdweb.team9.controller.admin;

import lombok.Getter;
import lombok.Setter;
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
    private String logo;
    private String image;
    private String openTime;
    private String closeTime;
    private boolean isActive;
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<Food> listFood = new ArrayList<>();
    private List<RatingRestaurant> listRatingRestaurant = new ArrayList<>();
    private List<Order> listOrder = new ArrayList<>();
}
