package vn.hdweb.team9.domain.dto.order;

import lombok.Data;

@Data
public class OrderDetailRequestDto {
    private Long foodId;
    private int quantity;
    private boolean isRatingFood;
}
