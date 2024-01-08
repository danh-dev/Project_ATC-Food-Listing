package vn.hdweb.team9.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.hdweb.team9.domain.dto.ItemCart;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price")
    private int unitPrice;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "is_rating_food")
    private boolean isRatingFood;

    @Column(name = "create_at")
    private LocalDateTime createdAt = LocalDateTime.now();


    public static OrderDetail createOrderDetail(ItemCart itemCart, Order order) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setFood(itemCart.getFood());
        orderDetail.setOrder(order);
        orderDetail.setFoodName(itemCart.getFood().getFoodName());
        orderDetail.setQuantity(itemCart.getQuantity());
        orderDetail.setUnitPrice(itemCart.getFood().getPrice());
        orderDetail.setTotalPrice(itemCart.getFood().getPrice() * itemCart.getQuantity());
        orderDetail.setRatingFood(false);
        return orderDetail;
    }

    public static void toggleRatingFood(OrderDetail orderDetail) {
        orderDetail.setRatingFood(!orderDetail.isRatingFood());
    }
}
