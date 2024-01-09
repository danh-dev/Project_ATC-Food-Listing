package vn.hdweb.team9.domain.dto.respon;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingRestaurantDto {
    private Long id;
    private String content;
    private int rateStar;
    private LocalDateTime createdAt ;
    private String restaurantName;
    private String userFullName;
    private String userAvatar;

}
