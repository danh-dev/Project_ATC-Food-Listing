package vn.hdweb.team9.domain.dto.respon;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RestaurantDto {
    private Long id;
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
}
