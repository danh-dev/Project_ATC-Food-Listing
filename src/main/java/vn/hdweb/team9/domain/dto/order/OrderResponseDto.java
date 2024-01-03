package vn.hdweb.team9.domain.dto.order;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class OrderResponseDto {
    private Long orderId;
    private Long userId;
    private String userName;
    private Long restaurantId;
    private String restaurantName;
    private String orderNote;
    private boolean isRatingRestaurant;
    private int totalBill;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderDetailResponseDto> orderDetails;
}
