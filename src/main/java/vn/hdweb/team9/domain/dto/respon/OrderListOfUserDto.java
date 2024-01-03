package vn.hdweb.team9.domain.dto.respon;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderListOfUserDto {
    private Long orderId;
    private String restaurantName;
    private String res_slug;
    private String deliveryAddress;
    private String totalBill;
    private String status;
    private boolean isRatingRestaurant;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
