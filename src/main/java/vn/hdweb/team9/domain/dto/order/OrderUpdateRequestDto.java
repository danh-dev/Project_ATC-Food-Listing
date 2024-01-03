package vn.hdweb.team9.domain.dto.order;

import lombok.Data;
import vn.hdweb.team9.domain.entity.OrderStatus;

@Data
public class OrderUpdateRequestDto {

    private OrderStatus newStatus;

}
