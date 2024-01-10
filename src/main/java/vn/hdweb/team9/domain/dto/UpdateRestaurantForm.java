package vn.hdweb.team9.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class UpdateRestaurantForm {
    private Long id;
    @NotEmpty(message = "Đây là trường bắt buộc")
    private String restaurantName;
    @NotEmpty(message = "Đây là trường bắt buộc")
    private String slug;
    private String description;
    @NotEmpty(message = "Đây là trường bắt buộc")
    private String address;
    private MultipartFile image;
    @NotEmpty(message = "Đây là trường bắt buộc")
    private String openTime;
    @NotEmpty(message = "Đây là trường bắt buộc")
    private String closeTime;
    //private boolean isActive = true;
}

