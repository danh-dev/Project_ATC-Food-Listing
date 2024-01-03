package vn.hdweb.team9.domain.dto.order;
import lombok.Data;

@Data
public class OrderDetailResponseDto {
    private Long orderDetailId;
    private Long foodId;
    private String foodName;
    private int quantity;
    private boolean isRatingFood;
    private int totalPrice;
}
