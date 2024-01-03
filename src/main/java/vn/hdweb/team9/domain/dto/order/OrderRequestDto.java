package vn.hdweb.team9.domain.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private Long userId;
    private Long restaurantId;
    private String orderNote;
    private boolean isRatingRestaurant;
    private List<OrderDetailRequestDto> orderDetails;
}
