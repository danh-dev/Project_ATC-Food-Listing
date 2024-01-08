package vn.hdweb.team9.controller.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class UpdateRestaurantForm {
    private Long id;
    private String restaurantName;
    private String slug;
    private String description;
    private String address;
    private MultipartFile image;
    private String openTime;
    private String closeTime;
    //private boolean isActive = true;
}

