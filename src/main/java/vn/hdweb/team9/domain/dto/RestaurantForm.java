package vn.hdweb.team9.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "Đây là trường bắt buộc")
    private String restaurantName;

    @NotEmpty(message = "Đây là trường bắt buộc")
    private String description;

    @NotEmpty(message = "Đây là trường bắt buộc")
    private String address;

    @NotNull(message = "Đây là trường bắt buộc")
    private MultipartFile image;

    @NotEmpty(message = "Đây là trường bắt buộc")
    private String openTime;

    @NotEmpty(message = "Đây là trường bắt buộc")
    private String closeTime;
    //private boolean isActive = true;
}
