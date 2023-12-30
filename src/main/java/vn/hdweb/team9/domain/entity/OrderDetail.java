package vn.hdweb.team9.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "is_rating_food")
    private boolean isRatingFood;

    @Column(name = "create_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
